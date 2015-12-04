# World-3-Identity-Kamikaze-Pilots
Solving Identity Crisis i.e. World 3

We have developed this assuming we are ASP

Our App provides two options to perform KYC

1] Users upload their e-sign documents

2] Users upload their scanned/photo copy of their documents and then ASP (Application Service Providers) send this to e-Mudhra for getting these documents signed.

So, documents are digitally signed either way.

APP FLOW-

1] User signs up to our app and enters basic details like First name, Last name, UID, DOB, E-mail and password.
UID is send to UIDAI server and server sends OTP to the registered mobile no.

Now, what if user's phone is sacrificied , we have BIOMETRIC for that.

- Ask for Finger print or
- Iris detection 

as this information is already stored in UIDAI's database.

Email and password is normal way of authenticating user. This is also provided as mobile no. stored in UIDAI's database may be outdated.So, we still have to provide normal ways of authentication.
Other Idea - We can implement Typing pattern matching OR Voice verification apart from OTP .

2] After signing up, User has two options as stated
- Upload e-sign documents (How?  https://esign.e-mudhra.com/ OR https://esign.cdac.in/)
- Upload scanned/photo of your documents , Banks will verify this . Banks already pay for performic KYC.

Just two steps and KYC process is done. So, user finally has mobile wallet with limit upto Rs.1,00,000/-.

