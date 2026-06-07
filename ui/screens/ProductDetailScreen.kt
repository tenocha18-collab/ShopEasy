package com.shopeasy.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shopeasy.app.data.entity.Product
import com.shopeasy.app.ui.theme.*
import com.shopeasy.app.viewmodel.CartViewModel
import com.shopeasy.app.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var product by remember { mutableStateOf<Product?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(productId) {
        product = productViewModel.getProductById(productId)
        isFavorite = cartViewModel.isFavorite(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "Товар") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        cartViewModel.toggleFavorite(productId)
                        isFavorite = !isFavorite
                    }) {
                        Icon(
                            Icons.Default.Favorite,
                            "Избранное",
                            tint = if (isFavorite) Primary else TextSecondary
                        )
                    }
                }
            )
        }
    ) { padding ->
        product?.let { p ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AsyncImage(
                    model = p.imageUrl,
                    contentDescription = p.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Категория
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Primary.copy(alpha = 0.1f)
                    ) {
                        Text(
                            p.category,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = Primary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(p.name, style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, null, tint = Warning, modifier = Modifier.size(20.dp))
                        Text(" ${p.rating}", fontSize = 16.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            if (p.inStock) "✓ В наличии" else "✗ Нет в наличии",
                            color = if (p.inStock) Success else Error,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(p.description, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "${p.price.toInt()} ₽",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                cartViewModel.addToCart(p.id, p.name, p.price)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        enabled = p.inStock
                    ) {
                        Icon(Icons.Default.ShoppingCart, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Добавить в корзину", fontSize = 16.sp)
                    }
                }
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}