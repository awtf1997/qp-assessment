# qp-assessment
Problem Statement: To design a grocery booking app
Roles:
- Admin
- User
API endpoints
1. Admin APIs:
   APIs contain role header as ADMIN
   - Add new grocery items to the system (POST : /inventory/items)
   - View existing grocery items (GET : /inventory/items)
   - Remove grocery items from the system (DELETE : /inventory/items/:item_id)
   - Update details (e.g., name, price) of existing grocery items (PUT : /inventory/items/:item_id?name=:name&price=:price)
   - Manage inventory levels of grocery items(PUT : /inventory/items/:item_id?quantity=:quantity)
3. User APIs:
   APIs contain role header as USER
   - View the list of available grocery items (GET : /inventory/items)
   - Ability to book multiple grocery items in a single order (POST : /order)
Deployment: Docker containers.
Database: MySQL
