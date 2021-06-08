# Concept
Hyperledger Fabric Example Created for JAVA.

# IMPORTANT BEFORE CLONE!

On Windows 10 you should use the native Docker distribution and you may use the Windows PowerShell. However, for the binaries command to succeed you will still need to have the uname command available. You can get it as part of Git but beware that only the 64bit version is supported.

Before running any git clone commands, run the following commands:

git config --global core.autocrlf false
git config --global core.longpaths true
You can check the setting of these parameters with the following commands:

git config --get core.autocrlf
git config --get core.longpaths
These need to be false and true respectively.

# Software Pre-requisites
- Docker (https://docs.docker.com/get-docker/)
- Visual Studio Code
  - Plugins : Required to Run
    - Java Extension Pack
    - IBM Blockchain Platform
    - Open Liberty Tools
  - Plugins : To create
    - Tools for Microprofile
    - Microprofile Starter
    - Gradle Language Support
    - Gradle Tasks
- Java 11 : for the above plugins
- Java 8 : For the PROJECT** (I flound that the Hyperledger Fabric Java SDK is only stable with Java 8 Version)
- Maven Compiler (https://www.javatpoint.com/how-to-install-maven)
- Gradle Compiler (Only required to create a new Gradle Project)

# How to Run

1. Clone the Git into your workspace (**FOLLOW the WINDOWS 10 Special requirements to avoid any issues)

2. Import the two folders (Chaincode & resp-aoi) seperately into your workspace.

3. Open the IBM Blockchain Platform Plugin and Create a new environment.
   - Click create new environment. 
  ![image](https://user-images.githubusercontent.com/76457616/121153471-61435280-c846-11eb-82c3-54c1e7ddff36.png)
   - Select "Create new from template".
  ![image](https://user-images.githubusercontent.com/76457616/121153975-ceef7e80-c846-11eb-9ab0-c60b21b607e9.png)
   - Select "1 Org template"
   ![image](https://user-images.githubusercontent.com/76457616/121154202-11b15680-c847-11eb-92c9-bac7dc268417.png)
   - Give a name for the environment.
   - Select Channel Capability version 2.0
   - Finally you will be able to see the new environement you created is avalable in the FABRIC ENVIRONMENTS tab.
   - Click on the new environment and it will be created. (New Docker Container will run in background.)

4. Package the new chaincode.
   - Goto the SMART CONTRACTS tab in the IBM Blockchain Platform plugin.
   - Click the three dot icon and select "Package Open Project".** Make sure the Chaincode folder is added to your worksapce.
   ![image](https://user-images.githubusercontent.com/76457616/121155294-f6931680-c847-11eb-9ffd-7c17ff10ea02.png)
   - Select the Chaincode Project from the drop down menu.
   ![image](https://user-images.githubusercontent.com/76457616/121155582-3659fe00-c848-11eb-8725-9b47e260536c.png)
   - Select the packaging format "tar.gz"
   ![image](https://user-images.githubusercontent.com/76457616/121155665-52f63600-c848-11eb-99d4-c34b72cdd8a3.png)
   - Set the name to any name you like. I use "asset"
   - Set the version of the package to "0.0.1"
   - Once click enter the package will be created and can be seen under SMART CONTRACTS tab.
   ![image](https://user-images.githubusercontent.com/76457616/121155932-8a64e280-c848-11eb-82d4-d8286af956f5.png)

5. Deploy the new contract into the blockchain.
   - Double click on the new environment you've created.
   ![image](https://user-images.githubusercontent.com/76457616/121156093-b08a8280-c848-11eb-8b93-2df8ee708e12.png)
   - Once logged into the environment, click "Deploy smart contract" under mychannel.
   ![image](https://user-images.githubusercontent.com/76457616/121156427-f5aeb480-c848-11eb-8484-61b68c01deec.png)
   - The "Deploy Smart Contract" Window will open.
   - In the new window select the package you've created before.
   ![image](https://user-images.githubusercontent.com/76457616/121156726-40c8c780-c849-11eb-86bd-0202daf566fb.png)
   - Click NEXT and skip both Step 2 and 3.
   - Finally click "Deploy".

6. Test the chaincode deployment is success
   - Double click on the  new gateway under FABRIC GATEWAYS
   ![image](https://user-images.githubusercontent.com/76457616/121157174-9d2be700-c849-11eb-81bf-14957957c938.png)
   - If the deployment successful you will be able to see the created chaincode functions as shown below.
   ![image](https://user-images.githubusercontent.com/76457616/121157326-c0569680-c849-11eb-9cbc-87f10cbc4812.png)

7. Create and enroll a new user.
   - Goto back to the FABRIC ENVIRONMENT.
   - Under Nodes you can find the Org1 Certificate Authority (Org1 CA).
   - Right click on it and then click "Create Identity".
   ![image](https://user-images.githubusercontent.com/76457616/121161824-a6b74e00-c84d-11eb-9327-4ca0cfb0fab8.png)
   - Insert a name (Short name reccomended) Ex. "sachi".
   - Select No to add attributes.
   - If created successfully, you can find the newly created account under FABRIC WALLETS tab.
  ![image](https://user-images.githubusercontent.com/76457616/121162131-f138ca80-c84d-11eb-8f8d-129771209a2d.png)
   
9. Deploy the REST API
   - Goto the "LIBERTY DEV DASHBOARD" in the explorer. 
   - If you have already loaded the rest-api folder to your workspace you can find it here.
   ![image](https://user-images.githubusercontent.com/76457616/121157730-13c8e480-c84a-11eb-9414-2d2e960fd7e2.png)
   - Right click on the rest-api liberty project and click "Start"
   ![image](https://user-images.githubusercontent.com/76457616/121162433-35c46600-c84e-11eb-8151-969fd1ff1454.png)
   - This will let Open Liberty Plugin to compile and start the REST API server.
   - Once the server started, access the API user interface via http://localhost:9080/openapi/ui

10. Load the Connection settings and wallet to the API in order to access the blockchain.
    - Export the Connection Profile from the FABRIC GATEWAY.
    ![image](https://user-images.githubusercontent.com/76457616/121163333-f9453a00-c84e-11eb-89d2-05f656926b09.png)
    - Copy the configurtion file into the below path
      - target\liberty\wlp\usr\servers\rest-api\Users\Shared\Connections
    - Export the Wallet
    ![image](https://user-images.githubusercontent.com/76457616/121163382-04986580-c84f-11eb-9d6b-e882ad9776b0.png)
    - Copy the new user ID created in the above step 7 into the wallet folder.
      - target\liberty\wlp\usr\servers\rest-api\Users\Shared\Connections\wallet
    ![image](https://user-images.githubusercontent.com/76457616/121164082-9c964f00-c84f-11eb-9893-ab127c2d3779.png)

11. Update the Configuration file and user ID in the REST API code.
    - Update the config file name and the user in the Iasset.java file under the below path.
      - src\main\java\Hyperledger\rest\api\openapi\IAsset.java
    ![image](https://user-images.githubusercontent.com/76457616/121165144-58577e80-c850-11eb-8682-3d8f09bb4149.png)
    - Save the file and you will be able to see on the Integrated terminal that Open Liberty plugin will automatically compile the files and reload the API.

12. Test the Functions and Have fun!

      

   
