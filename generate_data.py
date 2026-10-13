import csv
import random
import time
import os

random.seed(42)

DATA_DIR = "data"
for d in ["products", "customers", "reviews", "transactions", "warehouses", "stores", "inventory", "activities"]:
    os.makedirs(os.path.join(DATA_DIR, d), exist_ok=True)

# 1. Generate Products (approx 150)
categories = ["Smartphones", "Laptops", "Tablets", "Headphones", "Keyboards", "Mice", "Monitors", "Smart Watches", "Cameras", "Accessories"]
brands = {
    "Smartphones": ["Apple", "Samsung", "Google", "OnePlus", "Xiaomi"],
    "Laptops": ["Dell", "Lenovo", "HP", "Apple", "Asus"],
    "Tablets": ["Apple", "Samsung", "Lenovo", "Microsoft"],
    "Headphones": ["Sony", "Bose", "Sennheiser", "Apple", "Jabra"],
    "Keyboards": ["Logitech", "Keychron", "Razer", "Corsair"],
    "Mice": ["Logitech", "Razer", "SteelSeries", "Apple"],
    "Monitors": ["Dell", "LG", "Samsung", "Asus", "Acer"],
    "Smart Watches": ["Apple", "Samsung", "Garmin", "Fitbit"],
    "Cameras": ["Sony", "Canon", "Nikon", "Fujifilm"],
    "Accessories": ["Anker", "Belkin", "Spigen", "Apple"]
}

adjectives = ["Pro", "Max", "Ultra", "Slim", "Elite", "Plus", "Lite", "Advanced", "Essential", "Premium"]

products = []
for i in range(1, 151):
    pid = f"P{i:04d}"
    cat = random.choice(categories)
    brand = random.choice(brands[cat])
    model_num = random.randint(1, 99)
    adj = random.choice(adjectives)
    
    # Introduce some intentional typos for future fuzzy search
    if random.random() < 0.05:
        brand = brand.replace("Apple", "Aple").replace("Samsung", "Samzung").replace("Logitech", "Logitehc").replace("Sony", "Sonny")
    
    name = f"{brand} {cat[:-1] if cat.endswith('s') else cat} {model_num} {adj}"
    if cat == "Smartphones" and brand == "Apple": name = f"iPhone {model_num} {adj}"
    elif cat == "Smartphones" and brand == "Samsung": name = f"Samsung Galaxy S{model_num} {adj}"
    
    desc_words = ["excellent", "great", "amazing", "good", "battery", "display", "performance", "quality", "fast", "reliable", "compact", "durable"]
    desc = f"A {random.choice(desc_words)} {cat.lower()} with {random.choice(desc_words)} {random.choice(['battery life', 'display', 'performance'])}. "
    desc += f"Highly {random.choice(['recommended', 'rated'])} for {random.choice(['professionals', 'students', 'gamers', 'everyday use'])}."
    
    if random.random() < 0.05:
        desc = desc.replace("battery", "batery").replace("display", "disply").replace("performance", "performence")
    
    price = round(random.uniform(19.99, 1999.99), 2)
    rating = round(random.uniform(3.0, 5.0), 1)
    stock = random.randint(0, 500)
    wid = f"W{random.randint(1, 5):03d}"
    sales = random.randint(0, 1000)
    
    products.append([pid, name, cat, desc, price, rating, stock, wid, sales])

with open(f"{DATA_DIR}/products/products.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["productId", "name", "category", "description", "price", "rating", "stock", "warehouseId", "salesCount"])
    writer.writerows(products)

# 2. Generate Customers (approx 120)
first_names = ["Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona", "George", "Hannah", "Ian", "Julia", "Kevin", "Laura", "Mike", "Nina", "Oscar", "Paula", "Quinn", "Rachel", "Sam", "Tina"]
last_names = ["Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"]

customers = []
for i in range(1, 121):
    cid = f"C{i:04d}"
    name = f"{random.choice(first_names)} {random.choice(last_names)}"
    customers.append([cid, name])

with open(f"{DATA_DIR}/customers/customers.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["customerId", "name"])
    writer.writerows(customers)

# 3. Generate Warehouses (5)
warehouses = []
locations = ["New York", "Los Angeles", "Chicago", "Houston", "Phoenix"]
for i in range(1, 6):
    wid = f"W{i:03d}"
    loc = locations[i-1]
    cap = random.randint(5000, 20000)
    warehouses.append([wid, loc, cap])

with open(f"{DATA_DIR}/warehouses/warehouses.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["warehouseId", "location", "capacity"])
    writer.writerows(warehouses)

# 4. Generate Stores (15)
stores = []
for i in range(1, 16):
    sid = f"S{i:03d}"
    loc = f"City Center {i}"
    demand = random.randint(100, 1000)
    stores.append([sid, loc, demand])

with open(f"{DATA_DIR}/stores/stores.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["storeId", "location", "demand"])
    writer.writerows(stores)

# 5. Generate Inventory (300)
inventory = []
for i in range(300):
    wid = f"W{random.randint(1, 5):03d}"
    pid = f"P{random.randint(1, 150):04d}"
    qty = random.randint(10, 500)
    inventory.append([wid, pid, qty])

with open(f"{DATA_DIR}/inventory/inventory.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["warehouseId", "productId", "quantity"])
    writer.writerows(inventory)

# 6. Generate Transactions (1200)
transactions = []
current_time = int(time.time() * 1000)
for i in range(1, 1201):
    tid = f"T{i:05d}"
    cid = f"C{random.randint(1, 120):04d}"
    p = random.choice(products)
    pid = p[0]
    qty = random.randint(1, 3)
    amt = round(qty * p[4], 2)
    ts = current_time - random.randint(1000, 10000000)
    transactions.append([tid, cid, pid, qty, amt, ts])

with open(f"{DATA_DIR}/transactions/transactions.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["transactionId", "customerId", "productId", "quantity", "amount", "timestamp"])
    writer.writerows(transactions)

# 7. Generate Reviews (600)
reviews = []
review_texts = [
    "Excellent battery life, highly recommend.",
    "Great display and performance.",
    "Battery life is amazing, but a bit heavy.",
    "Good display quality, worth the price.",
    "Excellent display, very sharp.",
    "Poor battery backup, need to charge daily.",
    "I love my new {product_type}!",
    "Decent, but I expected more.",
    "The best {product_type} I've ever owned.",
    "Terrible customer service when I had issues.",
    "Works as advertised.",
    "Not bad, but not great either.",
    "My samzung is better.",
    "The keybord is clicky.",
    "Love the headphnes, great sound.",
    "Iphnoe is the best!"
]

for i in range(1, 601):
    rid = f"R{i:04d}"
    pid = f"P{random.randint(1, 150):04d}"
    cid = f"C{random.randint(1, 120):04d}"
    rt = random.choice(review_texts)
    # Get product cat
    cat = next(p[2] for p in products if p[0] == pid)
    rt = rt.replace("{product_type}", cat.lower()[:-1] if cat.endswith('s') else cat.lower())
    
    rating = round(random.uniform(1.0, 5.0), 1)
    if "Excellent" in rt or "Great" in rt or "amazing" in rt or "love" in rt or "best" in rt:
        rating = round(random.uniform(4.0, 5.0), 1)
    elif "Poor" in rt or "Terrible" in rt or "bad" in rt:
        rating = round(random.uniform(1.0, 2.5), 1)
        
    reviews.append([rid, pid, cid, rt, rating])

with open(f"{DATA_DIR}/reviews/reviews.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["reviewId", "productId", "customerId", "reviewText", "rating"])
    writer.writerows(reviews)

# 8. Generate Activities (1500)
activities = []
act_types = ["VIEW", "SEARCH", "ADD_TO_CART", "PURCHASE"]
for i in range(1, 1501):
    aid = f"A{i:05d}"
    cid = f"C{random.randint(1, 120):04d}"
    atype = random.choice(act_types)
    pid = f"P{random.randint(1, 150):04d}" if atype != "SEARCH" else ""
    ts = current_time - random.randint(1000, 10000000)
    activities.append([aid, cid, atype, pid, ts])

with open(f"{DATA_DIR}/activities/activities.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["activityId", "customerId", "activityType", "productId", "timestamp"])
    writer.writerows(activities)

print("Data generation complete.")
