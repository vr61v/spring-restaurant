# Spring restaurant

## Используемые технологии
* Spring framework (web, security, data, openfeign)
* PostgreSQL, MongoDB
* Oauth2
* Junit

## Роли в микросервисах
- Пользователь - не аутентифицированный в системе человек
- Заказчик - пользователь с ролью CUSTOMER
- Администратор - пользователь с ролью ADMIN
- Повар - пользователь с ролью COOK

## Микросервис карт
Микросервис карт отвечает за управление картами в системе. К нему имеет доступ только
заказчик и администратор. У заказчика есть права на получение или удаление своих карт
по их id или номеру, ко всем остальным возможностям сервиса имеет доступ только 
администратор. Для создания карты необходимо указать id пользователя, к которому будет
привязана эта карта, номер - строка, состоящая из 16 цифр, тип карты и скидку (число 
от 0 до 1), которую предлагает данная карта. Для обновления карты можно не указывать 
некоторые поля, в таком случае они останутся нетронутыми.

## Микросервис продуктов
Микросервис продуктов предоставляет api для управления данными о продуктах. 
У пользователей есть полный доступ к получению любого продукта, но для изменения 
данных нужно обладать ADMIN ролью. В запросах на создание/обновление продуктов 
проверяется валидность введенных данных (название не может быть пустым, а цена и 
вес не может быть меньше 0). В случае обновления некоторые поля могут быть не 
указаны, в таком случае они останутся нетронутыми.

## Микросервис ресторанов
Микросервис ресторанов предоставляет api для управления данными о ресторанах. 
У пользователей есть полный доступ к получению любого ресторана, но для изменения 
данных нужно обладать ADMIN ролью. В запросах на создание/обновление ресторана 
проверяется валидность введенных данных (название не может быть пустым, номер 
телефона должен быть введён корректно а все другие поля не могут быть null).

## Микросервис заказов
Микросервис заказов отвечает за управление заказами в системе. К нему имеет доступ 
любой авторизированный пользователь, но у каждого есть ограничения. Заказчик имеет 
право на создание, получение, и оплату своих заказов. Администратор может выполнять 
любую CRUD операцию. Повар имеет право получить заказы и изменить их статус. При 
некорректном состоянии заказа или нехватки средств для оплаты будет возвращено 
соответствующее исключение.

