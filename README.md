# PayHere Java Payment Integration Template

This project is a Java-based PayHere payment gateway integration template. It allows you to generate payment requests, handle PayHere notifications, and display success or cancellation pages for sandbox testing.

---

## Key Features

- **Generates MD5-based hashes for secure payment requests.**  

- **Provides a REST API to create payment requests.**  

- **Handles payment notifications from PayHere.**  

- **Demo frontend to initiate payments.**

- **Sample success and cancellation pages.**  


---


## Project Structure  

```
PayHereJavaTemplate//
│
├── src/
│   ├── controller/
│   │   ├── java/com/quizbot/quizbot_springboot_server/
│   │   │   ├── PaymentServlet.java        // Generates PayHere hash and payment request
│   │   │   └── PayHereNotifyServlet.java  // Handles payment notifications
│   │   │      
│   │   └── resources/
│   │       └── application.properties
│   └── util/
│       └── PayHereUtil.java           // MD5 hash utility and PayHere hash generator
│        
├── web/
│   ├──  index.html   // Demo payment page
│   ├──  success.html // Payment success page
│   └──  cancel.html  // Payment cancelled page
│      
│     
└── pom.xml / build.gradle              // Project dependencies (if using Maven/Gradle)
```

## Prerequisites

-  Java Development Kit (JDK) – Version 8 or above.
-  Servlet Container – Apache Tomcat (or any Java web server).
-  Internet Access – To load PayHere JS SDK from `https://www.payhere.lk/lib/payhere.js.`
-  Optional: IDE such as IntelliJ IDEA, Eclipse, or NetBeans.


---

##  Setup Instructions

1. Clone / Download Project

Download or clone the repository to your local machine.

```sh
git clone https://github.com/Avishka14/PayHere-Java-Template
```

2. Configure Merchant Credentials

Edit `PaymentServlet.java` and replace:

```sh
private final String MERCHANT_ID = "<YOUR_MERCHANT_ID>";
private final String MERCHANT_SECRET = "<YOUR_MERCHANT_SECRET>";
```

- MERCHANT_ID: Provided by PayHere sandbox/live account.
- MERCHANT_SECRET: Provided by PayHere.

Note: Sandbox mode is enabled by default (sandbox = true).

2. Build and Deploy

Build your Java web application (`.war` file) or deploy directly from IDE.

Deploy it to Tomcat at `http://localhost:8080/Payhere`.

4. Test Files

Ensure these HTML files are in the web root:

- `index.html` → Demo payment page

- `success.html` → Payment success page

- `cancel.html` → Payment cancel page


## Step-by-Step Usage
Step 1: Start Tomcat

- Run Tomcat server and ensure the project is deployed.

- Navigate to `http://localhost:8080/Payhere/index.html`.

Step 2: Initiate Payment

-Click “Pay LKR 100” button on the demo page.

-This triggers a POST request to:

```sh
http://localhost:8080/Payhere/api/payhere/hash
```
- `PaymentServlet` generates a secure PayHere hash and returns JSON data including order details.

Step 3: PayHere Popup

- PayHere JavaScript SDK opens a popup window:

   - Enter sandbox test card credentials (check PayHere docs for test card info).

   - Payment status callbacks:

      - `onCompleted(orderId)` → redirects to `success.html` with order ID.

      - `onDismissed()` → alerts payment cancelled.

      - `onError(err)` → alerts payment error.
        
Step 4: Notification Handling

- PayHere sends server-to-server notifications to:

```sh
http://localhost:8080/Payhere/payhere/notify
```
- `PayHereNotifyServlet` receives `POST` parameters:

`order_id`, `status_code`, `md5sig`, `payhere_amount`

## Expected Output

1. Payment Page

- Demo page with Pay LKR 100 button:

- Page shows:
```sh
PayHere Demo
PayHere JAVA Template by Avishka Chamod
```

2. Payment Success

- Redirected page shows:
```sh
✅ Payment Successful!
Thank you for your payment.
Order ID: ORDER_<timestamp>
[Go to Home button]
```

3. Payment Cancelled

- If user cancels payment:
```sh
❌ Payment Cancelled
Your payment was not completed.
[Go Back button]
```

4. Browser Console

 - Successful payment triggers JSON response from PaymentServlet with:
```sh
{
  "sandbox": true,
  "merchant_id": "<MERCHANT_ID>",
  "return_url": "http://localhost:8080/Payhere/success.html",
  "cancel_url": "http://localhost:8080/Payhere/cancel.html",
  "notify_url": "http://localhost:8080/Payhere/notify",
  "order_id": "ORDER_1695792000000",
  "items": "Test Product",
  "amount": "100.00",
  "currency": "LKR",
  "hash": "<generated-hash>",
  "first_name": "John",
  "last_name": "Doe",
  "email": "john@example.com",
  "phone": "0712345678",
  "address": "No.123, Main Street",
  "city": "Colombo",
  "country": "Sri Lanka"
}
```

## Testing Guide

 - 1. Verify API Endpoint
```sh
curl -X POST http://localhost:8080/Payhere/api/payhere/hash
```
  Should return JSON with `hash` and `order_id`.

- 2. Check MD5 Hash
   `PayHereUtil.generatePayHereHash` must produce 32-character uppercase MD5.

- 3. Test PayHere Popup
   Click Pay → complete sandbox card payment → redirect to `success.html`.

- 4. Test Cancel
   Close or cancel popup → should alert and redirect to `cancel.html`.

- 5. Server Notification
   Verify `PayHereNotifyServlet` receives POST parameters correctly.

## Important Notes

- Sandbox testing only: `sandbox = true`. Change to `false` for live payments.

- Always verify MD5 hash sent from PayHere notifications.

- Customize `amount` dynamically via frontend or database.

- Integrate database updates in `PayHereNotifyServlet` for production.



**Designed & Developed by Avishka Chamod**
