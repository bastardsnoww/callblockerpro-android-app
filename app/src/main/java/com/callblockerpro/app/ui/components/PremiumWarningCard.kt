
@Composable
fun PremiumWarningCard(
    title: String,
    message: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Red.copy(alpha = 0.15f),
                        Red.copy(alpha = 0.05f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Red.copy(alpha = 0.5f),
                        Red.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        // Red Glow
        Box(
             modifier = Modifier
                 .align(Alignment.TopEnd)
                 .size(100.dp)
                 .offset(x = 20.dp, y = (-20).dp)
                 .background(
                     brush = Brush.radialGradient(
                         colors = listOf(Red.copy(alpha = 0.2f), Color.Transparent)
                     )
                 )
        )

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Red.copy(0.1f), CircleShape)
                    .border(1.dp, Red.copy(0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Warning, null, tint = Red, modifier = Modifier.size(24.dp))
            }
            
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 16.sp
                )
            }
            
            NeonButton(
                text = buttonText,
                onClick = onClick,
                modifier = Modifier.height(40.dp),
                color = Red,
                icon = null
            )
        }
    }
}
