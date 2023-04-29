package com.zahand0.cowboys.data

import androidx.core.graphics.toColorInt
import com.zahand0.cowboys.domain.model.BadgeModel
import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.domain.model.ProductModel
import com.zahand0.cowboys.domain.model.ProductSizeModel
import java.util.UUID

object StubData {

    private val productIds = (0..5).map {
        UUID.randomUUID().toString()
    }
    val productDetailsList = listOf(
        ProductDetailsModel(
            id = productIds[0],
            title = "PUMA ESS+ Tape Sweatpants TR cl",
            department = "Брюки спортивные",
            price = 4990,
            preview = "https://a.lmcdn.ru/product/R/T/RTLABD432202_19416359_1_v1_2x.jpg",
            badge = BadgeModel(
                value = "Хит сезона",
                color = "#2E7D32".toColorInt()
            ),
            images = listOf(
                "https://a.lmcdn.ru/img600x866/R/T/RTLABD432202_19416359_1_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/R/T/RTLABD432202_19416360_2_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/R/T/RTLABD432202_19416361_3_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/R/T/RTLABD432202_19416362_4_v1_2x.jpg",
            ),
            sizes = listOf(
                ProductSizeModel(
                    value = "S",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "M",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "L",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "XL",
                    isAvailable = false
                ),
                ProductSizeModel(
                    value = "2XL",
                    isAvailable = true
                ),
            ),
            description = "Данный товар является частью проекта Lamoda planet - специального раздела нашего каталога, где мы собрали экологичные, этичные, инклюзивные и благотворительные товары. При производстве данного товара не использовались химические соединения и вещества вредные для человека и окружающей среды, что подтверждено сертификатом OEKO-TEX®. Выбирая такой товар, вы вносите свой вклад в сохранение планеты.",
            details = listOf(
                "Состав: Хлопок - 68%, Полиэстер - 32%",
                "Размер товара на модели: M INT",
                "Параметры модели: 100-85-99",
                "Рост модели на фото: 189 см",
                "Высота: 30 см",
                "Длина по внутреннему шву: 80 см",
                "Длина по боковому шву: 110 см",
                "Ширина по низу: 15 см",
                "Сезон: Мульти",
                "Цвет: Черный",
                "Узор: Рисунки и надписи",
                "Вид спорта: Спорт стиль",
                "Страна производства: Китай",
                "Застежка: Шнурки",
                "Сохраняя планету: Товар имеет экологические, этические сертификаты",
                "Артикул: RTLABD432202",
            )
        ),
        //1
        ProductDetailsModel(
            id = productIds[1],
            title = "O'stin",
            department = "Брюки",
            price = 1999,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0FG82_17574293_1_v1_2x.jpg",
            badge = BadgeModel(
                value = "Хит сезона",
                color = "#2E7D32".toColorInt()
            ),
            images = listOf(
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0FG82_17574293_1_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0FG82_17574294_2_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0FG82_17574295_3_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0FG82_17574296_4_v1_2x.jpg",
            ),
            sizes = listOf(
                ProductSizeModel(
                    value = "S",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "M",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "L",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "XL",
                    isAvailable = false
                ),
                ProductSizeModel(
                    value = "2XL",
                    isAvailable = true
                ),
            ),
            description = "Данный товар является частью проекта Lamoda planet - специального раздела нашего каталога, где мы собрали экологичные, этичные, инклюзивные и благотворительные товары.",
            details = listOf(
                "Состав: Хлопок - 100%",
                "Размер товара на модели: S INT",
                "Параметры модели: 85-62-90",
                "Рост модели на фото: 177 см",
                "Высота: 35 см",
                "Длина по внутреннему шву: 70 см",
                "Длина по боковому шву: 103 см",
                "Ширина по низу: 17 см",
                "Сезон: Лето",
                "Цвет: Бежевый",
                "Узор: Однотонный",
                "Талия: Высокая",
                "Карманы: 2",
                "Комплектация: Брюки, пояс",
                "Гарантийный период: Не установлен",
                "Страна производства: Бангладеш",
                "Застежка: Без застежки",
                "Артикул: MP002XW0FG82",
            )
        ),
        //2
        ProductDetailsModel(
            id = productIds[2],
            title = "Barmariska",
            department = "Кардиган",
            price = 13999,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886905_1_v1.jpeg",
            badge = BadgeModel(
                value = "Хит сезона",
                color = "#2E7D32".toColorInt()
            ),
            images = listOf(
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886905_1_v1.jpeg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886906_2_v1.jpeg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886907_3_v1.jpeg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886908_4_v1.jpeg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886909_5_v1.jpeg",
            ),
            sizes = listOf(
                ProductSizeModel(
                    value = "S",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "M",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "L",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "XL",
                    isAvailable = false
                ),
                ProductSizeModel(
                    value = "2XL",
                    isAvailable = true
                ),
            ),
            description = "Женский кардиган выполнен из высококачественной пряжи. Модель оверсайз. Детали: V-образный вырез горловины, спущенные длинные рукава, застежка на пуговицах. Цвет и декоративные элементы изделия могут незначительно отличаться от фото.\n" +
                    "\n" +
                    "Данный товар является частью проекта Lamoda planet - специального раздела нашего каталога, где мы собрали экологичные, этичные, инклюзивные и благотворительные товары.\n" +
                    "\n" +
                    "Товар произведен в стране присутствия Lamoda, что позволяет нам оптимизировать выбросы СО2 при доставке. Покупая этот товар, вы вносите свой вклад в сокращение углеродного следа и поддерживаете развитие локальных фабрик.",
            details = listOf(
                "Состав: Акрил - 100%",
                "Размер товара на модели: 42/50",
                "Параметры модели: 86-60-89",
                "Рост модели на фото: 174 см",
                "Длина: 70 см",
                "Длина рукава: 42 см",
                "Сезон: Мульти",
                "Цвет: Мультиколор",
                "Узор: Животные",
                "Гарантийный период: 30 дней",
                "Страна производства: Россия",
                "Local: Товар произведен на локальных фабриках, экослед от доставки ниже",
                "Артикул: MP002XW0KJN0",
            )
        ),
        //3
        ProductDetailsModel(
            id = productIds[3],
            title = "PlayToday",
            department = "Куртка",
            price = 4799,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658844_1_v1.jpg",
            badge = BadgeModel(
                value = "Хит сезона",
                color = "#2E7D32".toColorInt()
            ),
            images = listOf(
                "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658844_1_v1.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658845_2_v1.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658846_3_v1.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658847_4_v1.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658848_5_v1.jpg",
            ),
            sizes = listOf(
                ProductSizeModel(
                    value = "S",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "M",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "L",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "XL",
                    isAvailable = false
                ),
                ProductSizeModel(
                    value = "2XL",
                    isAvailable = true
                ),
            ),
            description = "Куртка из материала c ветро- и водоотталкивающей пропиткой с подкладкой из флиса. Детали: несъемный капюшон, регулируемый эластичной резинкой; внутренняя ветрозащитная планка; регулируемый низ куртки шнурком со стопперами; эластичная резинка по низу рукава; два боковых кармана и внутренний карман; светоотражающие элементы.",
            details = listOf(
                "Состав: Полиэстер - 100%",
                "Материал подкладки: Флис - 100%",
                "Сезон: Демисезон",
                "Цвет: Мультиколор",
                "Узор: Рисунки и надписи",
                "Внутренние карманы: 1",
                "Карманы: 2",
                "Капюшон: Не съемный",
                "Гарантийный период: 30 дней",
                "Страна производства: Китай",
                "Застежка: Молния",
                "Артикул: MP002XB01G7M",
            )
        ),
        //4
        ProductDetailsModel(
            id = productIds[4],
            title = "Colin's",
            department = "Юбка",
            price = 1990,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW15ULK_17398729_1_v1_2x.jpg",
            badge = BadgeModel(
                value = "Хит сезона",
                color = "#2E7D32".toColorInt()
            ),
            images = listOf(
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW15ULK_17398729_1_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW15ULK_17398730_2_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW15ULK_17398731_3_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW15ULK_17398732_4_v1_2x.jpg",
            ),
            sizes = listOf(
                ProductSizeModel(
                    value = "S",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "M",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "L",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "XL",
                    isAvailable = false
                ),
                ProductSizeModel(
                    value = "2XL",
                    isAvailable = true
                ),
            ),
            description = "Данный товар является частью проекта Lamoda planet - специального раздела нашего каталога, где мы собрали экологичные, этичные, инклюзивные и благотворительные товары.",
            details = listOf(
                "Состав: Вискоза - 71%, Полиэстер - 25%, Эластан - 4%",
                "Размер товара на модели: M INT",
                "Параметры модели: 84-60-92",
                "Рост модели на фото: 178 см",
                "Длина по боковому шву: 48 см",
                "Сезон: Мульти",
                "Цвет: Бежевый",
                "Узор: Однотонный",
                "Гарантийный период: 30 дней",
                "Страна производства: Турция",
                "Застежка: Без застежки",
                "Артикул: MP002XW15ULK",
            )
        ),
        //5
        ProductDetailsModel(
            id = productIds[5],
            title = "Befree",
            department = "Куртка утепленная",
            price = 3999,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771831_1_v1_2x.jpg",
            badge = BadgeModel(
                value = "Хит сезона",
                color = "#2E7D32".toColorInt()
            ),
            images = listOf(
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771831_1_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771832_2_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771833_3_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771834_4_v1_2x.jpg",
                "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771835_5_v1_2x.jpg",
            ),
            sizes = listOf(
                ProductSizeModel(
                    value = "S",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "M",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "L",
                    isAvailable = true
                ),
                ProductSizeModel(
                    value = "XL",
                    isAvailable = false
                ),
                ProductSizeModel(
                    value = "2XL",
                    isAvailable = true
                ),
            ),
            description = "Утепленная куртка выполнена из текстиля. Модель прямого кроя. Детали: застежка на молнии, внутренняя ветрозащитная планка, 2 боковых кармана без застежки, воротник-стойка, внутри гладкая подкладка, тонкий слой искусственного утеплителя.",
            details = listOf(
                "Состав: Полиэстер - 100%",
                "Материал подкладки: Полиэстер - 100%",
                "Утеплитель: Полиэстер - 100%",
                "Размер товара на модели: S INT",
                "Параметры модели: 84-59-90",
                "Рост модели на фото: 174 см",
                "Длина: 72 см",
                "Длина рукава: 68 см",
                "Сезон: Демисезон",
                "Цвет: Черный",
                "Узор: Однотонный",
                "Карманы: 2",
                "Капюшон: Не съемный",
                "Гарантийный период: Не установлен",
                "Страна производства: Китай",
                "Застежка: Молния",
                "Артикул: MP002XW0MF7Y",
            )
        ),
    )
    val products = listOf(
        ProductModel(
            id = productIds[0],
            title = "PUMA ESS+ Tape Sweatpants TR cl",
            department = "Брюки спортивные",
            price = 4990,
            preview = "https://a.lmcdn.ru/product/R/T/RTLABD432202_19416359_1_v1_2x.jpg"
        ),
        ProductModel(
            id = productIds[1],
            title = "O'stin",
            department = "Брюки",
            price = 1999,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0FG82_17574293_1_v1_2x.jpg"
        ),
        ProductModel(
            id = productIds[2],
            title = "Barmariska",
            department = "Кардиган",
            price = 13999,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886905_1_v1.jpeg"
        ),
        ProductModel(
            id = productIds[3],
            title = "PlayToday",
            department = "Куртка",
            price = 4799,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658844_1_v1.jpg"
        ),
        ProductModel(
            id = productIds[4],
            title = "Colin's",
            department = "Юбка",
            price = 1990,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW15ULK_17398729_1_v1_2x.jpg"
        ),
        ProductModel(
            id = productIds[5],
            title = "Befree",
            department = "Куртка утепленная",
            price = 3999,
            preview = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771831_1_v1_2x.jpg"
        ),
    )

    val ordersList = mutableListOf(
        OrderModel(
            id = UUID.randomUUID().toString(),
            number = 1234,
            product = products[0],
            productPreview = products[0].preview,
            productQuantity = 1,
            productSize = "XL",
            createdAt = "2023-04-15T18:31:42+03:00",
            etd = "2023-04-20T10:00:00+03:00",
            deliveryAddress = "г. Саранск, ул. Демократическая, 14",
            status = "in_work"
        ),
        OrderModel(
            id = UUID.randomUUID().toString(),
            number = 1234,
            product = products[0],
            productPreview = products[0].preview,
            productQuantity = 1,
            productSize = "XL",
            createdAt = "2023-04-15T18:31:42+03:00",
            etd = "2023-04-20T10:00:00+03:00",
            deliveryAddress = "г. Саранск, ул. Демократическая, 14",
            status = "cancelled"
        ),
        OrderModel(
            id = UUID.randomUUID().toString(),
            number = 2222,
            product = products[1],
            productPreview = products[1].preview,
            productQuantity = 1,
            productSize = "XL",
            createdAt = "2023-04-15T18:31:42+03:00",
            etd = "2023-04-20T10:00:00+03:00",
            deliveryAddress = "г. Саранск, ул. Демократическая, 14",
            status = "done"
        ),
        OrderModel(
            id = UUID.randomUUID().toString(),
            number = 3333,
            product = products[2],
            productPreview = products[2].preview,
            productQuantity = 5,
            productSize = "XXL",
            createdAt = "2023-04-16T18:31:42+03:00",
            etd = "2023-04-21T10:00:00+03:00",
            deliveryAddress = "г. Саранск, ул. Демократическая, 14",
            status = "in_work"
        ),
        OrderModel(
            id = UUID.randomUUID().toString(),
            number = 4444,
            product = products[3],
            productPreview = products[3].preview,
            productQuantity = 2,
            productSize = "XL",
            createdAt = "2023-04-15T18:31:42+03:00",
            etd = "2023-04-20T10:00:00+03:00",
            deliveryAddress = "г. Саранск, ул. Демократическая, 14",
            status = "cancelled"
        ),
    )

    fun cancelOrder(orderId: String): OrderModel? {
        val index = ordersList.indexOfFirst { it.id == orderId }
        if (index != -1) {
            ordersList[index] = ordersList[index].copy(status = "cancelled")
        }
        return ordersList.getOrNull(index)
    }


}
