
Check All the documents at :
    https://docs.google.com/document/d/1qWhLIojn5fEU_m__2Sz_pjDzyzncst4FwhSz2b2o6EY/edit?usp=sharing


----- WelcomeActivity ---------------------------------------------------------
1-> Welcome User : WelcomeActivity
    if ( User.login() ){
        // Check Whether Database set?
        if( isDatabaseSet() ){
            Shift User -> MainActivity;
        }else{
            GOTO : 1.1  SetDatabaseFragment
        }
    }else{
        Shift User -> AuthActivity;
    }

    1.1 SetDatabaseFragment : To set Database....
        1. Show UI to Set Database...
        2. And At the End ->
        3. Shift User -> MainActivity;

----- AuthActivity ---------------------------------------------------------
2-> AuthActivity : User Authentication And Sign In

    2.1 SignInFragment :
        2.1.1 : SignIn() :: User Can Sign in With Phone or Email Password.
            if(Valid()){
                if((Email() Or Mobile()).isRegistered()){
                    --> Proceed to QueryToLogin()
                }else -> Show Error?
            }

        2.1.2 : QueryToLogin() :: Now Check Database Set Or Not?
            --> if( isDatabaseSet() ){
                    Shift User -> MainActivity;
                }else{
                    GOTO: 1.1  SetDatabaseFragment
                }

        2.1.3 : ForgetPassword Button Click --> GOTO 2.3 : ForgetPasswordFragment
        2.1.4 : New User Button Action --> GOTO  2.2 : Registration

    2.2 : Registration Or Sign Up Fragment :
        2.1.1 : SignUp : Check Whether already registered or not?
                if(isValid()){
                    --> Check Whether User Mobile is Found on Database Or Not
                    if(Number Founded){
                        UploadAndRegistration();
                    }else{
                        --> Show Error Message: Contact to Owner.!
                    }
                }
        2.1.2 : UploadAndRegistration() :: Now Check Database Set Or Not?
                 --> if( isDatabaseSet() ){
                    Shift User -> MainActivity;
                }else{
                    GOTO: 1.1  SetDatabaseFragment
                }

    2.3 : ForgetPasswordFragment :
        2.3.1 ResetPassword() :
            --> User Can Reset Password Two Ways...
                --> 1. Can get Email To Reset or Change Their Password!
                --> 2. They Can Change Password Using their Mobile and OTP! if They Registered their Mobile


----- MainActivity ---------------------------------------------------------

TODO: Writing Algorithm

TODO List...
//----------- Add Feature
 * Add Option to sell Offline and generate Bill
 * In My Products 
    - Show A icon to scan product bar code : icon add done
    - On Click : Open product
 
//------------ Add below features

1. Manage Product View
    1.1 : Add Action to View All Products..
        - Update Product's info of each layout.
        - Open Product Details Activity
    1.2 : Add Action View All Category
        - Update Category Information
        - On Click Action for Category.
    1.3 : Add Slider View ( Banner )
        - Update and View all for slider.
        - Delete Slider item.
        
2. Selling Records
    2.1 : Show Graphs, Monthly Ups & Down
    2.2 : Show total Income Amount in the current Month
    2.3 : Button to view Selling Product's List With variant
    2.4 : On Click Item = Show Information by calculate amount etc.
    
3. Out Of Stocks Products
    3.1 : Show List of products
        - Out of Stocks items
        - Less Stocks Items 
        - OnClick Action - Update Stock Items
    3.2 : Show scan barcode icon
    3.2 : Search product to update Stocks
        
4. Manage Service Areas
    4.1 : Show List of Service Area List 
        - PinCode, Area Name
        - OnClick Action : Update Or Set Service Off
        - Delete Area Item Option
    4.2 : Service off for all area button at top menu
    4.3 : Add new Area Button - Bottom side...
    
5. Customer's Feedback 
    5.1 : List of User feedback...
        - Show User Name, Rating, Message, Good/Bad Rating indication
        - Send Reply to user onClick Action 
            * You can ask where they get problems etc
    5.2 : Show over all ratings, Total ratings