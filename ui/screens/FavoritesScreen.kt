package com.shopeasy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shopeasy.app.data.entity.Favorite
import com.shopeasy.app.data.entity.Product
import com.shopeasy.app.ui.theme.*
import com.shopeasy.app.viewmodel.CartViewModel
import com.shopeasy.app.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    val favorites by cartViewModel.favorites.collectAsState()
    val allProducts by productViewModel.products.collectAsState()
    val scope = rememberCoroutineScope()

    val favoriteProducts = favorites.mapNotNull { fav ->
        allProducts.find { it.id == fav.productId }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное ❤️") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.FavoriteBorder, null,
                        modifier = Modifier.size(80.dp),
                        tint = TextSecondary.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Нет избранных товаров", color = TextSecondary, fontSize = 18.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteProducts, key = { it.id }) { product ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.name,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(product.name, fontSize = 16.sp)
                                Text(
                                    "${product.price.toInt()} ₽",
                                    color = Primary, fontSize = 16.sp
                                )
                            }
                            IconButton(onClick = {
                                cartViewModel.addToCart(
                                    product.id, product.name, product.price
                                )
                            }) {
                                Icon(
                                    Icons.Default.AddShoppingCart,
                                    "В корзину",
                                    tint = Success
                                )
                            }
                            IconButton(onClick = {
                                cartViewModel.toggleFavorite(product.id)
                            }) {
                                Icon(
                                    Icons.Default.Favorite,
                                    "Убрать",
                                    tint = Error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}