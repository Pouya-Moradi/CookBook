package com.example.cookbook.ui.screen

// Import statements go here

// Composable function for displaying the food screen
@Composable
fun FoodScreen(
    food: Food, // Food object to display
    modifier: Modifier = Modifier // Modifier for styling
) {
    // Surface for holding the entire screen content
    Surface(modifier = modifier) {
        // Column for arranging content vertically
        Column(modifier = Modifier.background(color = Color(0xFFDBD0C0))) {
            // Box for holding the image and text
            Box {
                // AsyncImage for displaying the food image
                AsyncImage(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(), // Height and width of the image
                    model = food.imageUrl, // Image URL
                    contentDescription = null, // Description for accessibility
                    contentScale = ContentScale.Crop, // Scale type for content
                    alpha = 0.8f // Alpha value for image transparency
                )
                // Text displaying the food name
                Text(
                    text = food.name, // Name of the food
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceTint, // Background color for text
                            shape = RoundedCornerShape(8.dp) // Rounded corner shape for text background
                        )
                        .padding(4.dp)
                        .align(Alignment.BottomEnd), // Alignment of text
                    color = MaterialTheme.colorScheme.onPrimary, // Text color
                    style = MaterialTheme.typography.titleLarge // Typography style
                )
            }
            // Text displaying the food recipe
            Text(
                text = food.recipe, // Recipe of the food
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp) // Margin from the image above
                    .padding(horizontal = 25.dp) // Margin from the left and right sides of the screen
                    .padding(bottom = 8.dp), // Margin from the bottom of the screen
                textAlign = TextAlign.End // Text alignment
            )
        }
    }
}
