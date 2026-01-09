package com.callblockerpro.app.domain.util

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties

import com.callblockerpro.app.data.local.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.io.OutputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptographyManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceManager: PreferenceManager
) {
    private val ALGORITHM = "AES/CBC/PKCS7Padding"
    private val HEADER = "CBPRO_ENC".toByteArray()
    private val VERSION_SALTED: Byte = 0x02
    private val ITERATIONS = 10000
    private val KEY_LENGTH = 256

    fun encrypt(outputStream: OutputStream): OutputStream {
        outputStream.write(HEADER)
        
        val password = runBlocking { preferenceManager.backupEncryptionKey.first() }
        val cipher = Cipher.getInstance(ALGORITHM)
        
        if (password.isNotEmpty()) {
            // Version 2: Salted Password Encryption
            outputStream.write(VERSION_SALTED.toInt())
            val salt = ByteArray(16).apply { SecureRandom().nextBytes(this) }
            outputStream.write(salt)
            
            val key = deriveKey(password, salt)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            outputStream.write(cipher.iv)
        } else {
            // Version 1: KeyStore (Legacy)
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
            outputStream.write(cipher.iv)
        }
        
        return CipherOutputStream(outputStream, cipher)
    }

    fun decrypt(inputStream: InputStream): InputStream {
        val header = ByteArray(HEADER.size)
        inputStream.read(header)
        if (!header.contentEquals(HEADER)) {
            throw IllegalArgumentException("Not an encrypted backup")
        }

        val firstByte = inputStream.read()
        val cipher = Cipher.getInstance(ALGORITHM)
        
        if (firstByte == VERSION_SALTED.toInt()) {
            val salt = ByteArray(16)
            inputStream.read(salt)
            val iv = ByteArray(16)
            inputStream.read(iv)
            
            val password = runBlocking { preferenceManager.backupEncryptionKey.first() }
            if (password.isEmpty()) {
                throw IllegalStateException("Backup is password-protected but no encryption key is set in settings.")
            }
            
            val key = deriveKey(password, salt)
            cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        } else {
            // Legacy format: firstByte was actually the first byte of IV
            val iv = ByteArray(16)
            iv[0] = firstByte.toByte()
            inputStream.read(iv, 1, 15)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), IvParameterSpec(iv))
        }
        
        return CipherInputStream(inputStream, cipher)
    }

    private fun deriveKey(password: String, salt: ByteArray): java.security.Key {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1") 
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val tmp = factory.generateSecret(spec)
        return SecretKeySpec(tmp.encoded, "AES")
    }

    fun isEncrypted(inputStream: InputStream): Boolean {
        inputStream.mark(HEADER.size + 1)
        val header = ByteArray(HEADER.size)
        val read = inputStream.read(header)
        inputStream.reset()
        return read == HEADER.size && header.contentEquals(HEADER)
    }

    private fun getSecretKey(): java.security.Key {
        val keyStore = java.security.KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        // We use the same alias for simplicity, but technically MasterKeys is for Wrapping keys.
        // For direct AES, we should generate one if not exists.
        
        val alias = "backup_encryption_key"
        if (!keyStore.containsAlias(alias)) {
             val keyGenerator = KeyGenerator.getInstance(
                 KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"
             )
             keyGenerator.init(
                 KeyGenParameterSpec.Builder(
                     alias,
                     KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                 )
                 .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                 .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                 .build()
             )
             keyGenerator.generateKey()
        }
        return keyStore.getKey(alias, null)
    }
}
