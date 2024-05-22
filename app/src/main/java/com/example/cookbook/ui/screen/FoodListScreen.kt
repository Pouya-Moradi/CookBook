// Composable function for displaying the food list screen
@Composable
fun FoodListScreen(
    modifier: Modifier = Modifier, // Modifier for styling
    foodCategoryId: Int, // ID of the food category
    foods: List<Food>, // List of foods in the category
    onFoodItemClick: (Int) -> Unit // Callback for food item click
) {
    // Find the food category by ID
    val foodCategory = FoodDataProvider.foodCategories.find { it.id == foodCategoryId }

    // Surface for holding the entire screen content
    Surface(
        modifier = modifier.fillMaxSize().background(color = Color(0xFFF9E4C8)), // Styling and background color
        color = Color(0xFFF9E4C8) // Background color
    ) {
        // Column for arranging content vertically
        Column(
            modifier = Modifier.padding(15.dp), // Padding for content
            horizontalAlignment = Alignment.CenterHorizontally // Center align content horizontally
        ) {
            // Text displaying the name of the food category
            Text(
                modifier = Modifier.padding(50.dp), // Padding for the text
                text = foodCategory!!.name, // Name of the food category
                style = MaterialTheme.typography.displaySmall, // Typography style
                textAlign = TextAlign.Center // Text alignment
            )

            // Lazy column for displaying food items
            LazyColumn {
                items(foods, key = { food -> food.id }) { food ->
                    // Composable function for individual food item
                    FoodListItem(
                        food = food, // Food item
                        modifier = Modifier.padding(horizontal = 8.dp), // Padding for the item
                        onFoodItemClick // Callback for item click
                    )
                }
            }
        }
    }
}

// Composable function for displaying individual food item
@Composable
private fun FoodListItem(
    food: Food, // Food item
    modifier: Modifier = Modifier, // Modifier for styling
    onFoodItemClick: (Int) -> Unit // Callback for item click
) {
    // Surface for holding the item content
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp) // Height of the item
            .padding(vertical = 4.dp, horizontal = 12.dp) // Padding for the item
            .clickable { onFoodItemClick(food.id) }, // Clickable behavior
        shape = RoundedCornerShape(4.dp), // Rounded corner shape for the item
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary), // Border stroke for the item
        color = Color(0xFFF9CF93) // Background color for the item
    ) {
        // Row for arranging content horizontally
        Row(
            verticalAlignment = Alignment.CenterVertically, // Align content vertically
            horizontalArrangement = Arrangement.SpaceBetween // Space between content
        ) {
            // Text displaying the name of the food
            Text(
                text = food.name, // Name of the food
                modifier = Modifier.weight(1f), // Weight for layout
                textAlign = TextAlign.Center, // Text alignment
                color = MaterialTheme.colorScheme.onTertiaryContainer, // Text color
                style = MaterialTheme.typography.titleLarge // Typography style
            )

            // AsyncImage for displaying the image of the food
            AsyncImage(
                modifier = Modifier.weight(1f), // Weight for layout
                model = food.imageUrl, // Image URL
                contentDescription = null, // Description for accessibility
                contentScale = ContentScale.FillBounds // Scale type for content
            )
        }
    }
}