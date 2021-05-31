/*
 * SPDX-License-Identifier: Apache-2.0
 */
package Hyperledger.rest.api.openapi;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@javax.ws.rs.Path("/asset")
@ApplicationScoped
@OpenAPIDefinition(info = @Info(title = "asset endpoint", version = "1.0"))
public class IAsset {
    	
	// set this for the location of the wallet directory and the connection json
	String currentDir = "";
	Path currentUsersHomeDir = Paths.get(System.getProperty("user.dir"));
	Path pathRoot = Paths.get(currentUsersHomeDir.toString(), "Users", "Shared", "Connections");
	String connectionFile = "\\1OrgLocalFabricOrg1GatewayConnection.json";
    
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Asset for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Asset"))),
            @APIResponse(responseCode = "404", description = "No Asset found for the id.") })

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @javax.ws.rs.Path("assetID")
	@Operation(summary = "Check if Asset Exist in the Blockchain", description = "Requires the key to be provided")
    public Response assetExists(@QueryParam("assetId") String assetId) {
		byte[] result = null;

		try {
			Path walletPath = Paths.get(pathRoot.toString(), "wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);

			// load a CCP
			// expecting the connect profile json file; export the Connection Profile from
			// the
			// fabric gateway and add to the default server location
			Path networkConfigPath = Paths.get(pathRoot + connectionFile);
			Gateway.Builder builder = Gateway.createBuilder();

			// expecting wallet directory within the default server location
			// wallet exported from Fabric wallets Org 1
			builder.identity(wallet, "sachi").networkConfig(networkConfigPath).discovery(true);

			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				org.hyperledger.fabric.gateway.Contract contract = network.getContract("asset");
				result = contract.evaluateTransaction("assetExists", assetId);              
				return Response.status(Response.Status.OK).entity(new String(result)).build();
			} catch (Exception e) {
				System.out.println("Unable to get network/contract and execute query");
				throw new javax.ws.rs.ServiceUnavailableException();
			}
		} 
		catch (IOException e) {
			System.out.println("Current working dir: " + currentDir);
			System.out.println(
					"Unable to find config or wallet - please check the wallet directory and connection json");
			throw new javax.ws.rs.NotFoundException();
		}
		catch (Exception e)		
		{
			System.out.println(e.getMessage());
			throw new javax.ws.rs.ServiceUnavailableException();
		}
    }

    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Add a new Tag to the ledger", description = "Requires a unique key starting with TAG to be successfull")
	@javax.ws.rs.Path("createAsset")
    public Response createAsset(@QueryParam("assetId")String assetId,@QueryParam("value") String value) {
        try {
			currentDir = new java.io.File(".").getCanonicalPath();
			byte[] response = new String("Null").getBytes() ;

			Path walletPath = Paths.get(pathRoot.toString(), "wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);
			Path networkConfigPath = Paths.get(pathRoot + connectionFile);

			Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "sachi").networkConfig(networkConfigPath).discovery(true);
			try(Gateway gateway = builder.connect()) {
				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				org.hyperledger.fabric.gateway.Contract contract = network.getContract("asset");
				org.hyperledger.fabric.gateway.Transaction transaction = contract.createTransaction("createAsset");
				response = transaction.submit(assetId,value);
				return Response.status(Response.Status.OK).entity(new String(response)).build();
			} catch (ContractException e) {
				System.out.println("Unable to get network/contract and execute query : " + e.toString() + ", Response : " + e.getProposalResponses().toString() );
				throw new javax.ws.rs.ServiceUnavailableException();
			}
		} 
		catch (IOException e) {
			System.out.println("Current working dir: " + currentDir);
			System.out.println(
					"Unable to find config or wallet - please check the wallet directory and connection json");
			throw new javax.ws.rs.NotFoundException();
		}
		catch (Exception e)		
		{
			System.out.println(e.toString());
			throw new javax.ws.rs.ServiceUnavailableException();
		}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @javax.ws.rs.Path("readAsset")
	@Operation(summary = "Get all asset information from the Blockchain", description = "Requires the key to be provided")
    public Response readAsset(@QueryParam("assetId") String assetId) {
		byte[] result = null;

		try {
			Path walletPath = Paths.get(pathRoot.toString(), "wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);

			// load a CCP
			// expecting the connect profile json file; export the Connection Profile from
			// the
			// fabric gateway and add to the default server location
			Path networkConfigPath = Paths.get(pathRoot + connectionFile);
			Gateway.Builder builder = Gateway.createBuilder();

			// expecting wallet directory within the default server location
			// wallet exported from Fabric wallets Org 1
			builder.identity(wallet, "sachi").networkConfig(networkConfigPath).discovery(true);

			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				org.hyperledger.fabric.gateway.Contract contract = network.getContract("asset");
				result = contract.evaluateTransaction("readAsset", assetId);              
				return Response.status(Response.Status.OK).entity(new String(result)).build();
			} catch (Exception e) {
				System.out.println("Unable to get network/contract and execute query");
				throw new javax.ws.rs.ServiceUnavailableException();
			}
		} 
		catch (IOException e) {
			System.out.println("Current working dir: " + currentDir);
			System.out.println(
					"Unable to find config or wallet - please check the wallet directory and connection json");
			throw new javax.ws.rs.NotFoundException();
		}
		catch (Exception e)		
		{
			System.out.println(e.getMessage());
			throw new javax.ws.rs.ServiceUnavailableException();
		}
    }

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update Asset Information", description = "Requires the Asset ID and the New Value to be provided")
	@javax.ws.rs.Path("updateAsset")
    public Response updateAsset(@QueryParam("assetId") String assetId,@QueryParam("newValue") String newValue) {
        try {
			currentDir = new java.io.File(".").getCanonicalPath();
			byte[] response = new String("Null").getBytes() ;

			Path walletPath = Paths.get(pathRoot.toString(), "wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);
			Path networkConfigPath = Paths.get(pathRoot + connectionFile);

			Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "sachi").networkConfig(networkConfigPath).discovery(true);
			try(Gateway gateway = builder.connect()) {
				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				org.hyperledger.fabric.gateway.Contract contract = network.getContract("asset");
				org.hyperledger.fabric.gateway.Transaction transaction = contract.createTransaction("updateAsset");
				response = transaction.submit(assetId,newValue);
				return Response.status(Response.Status.OK).entity(new String(response)).build();
			} catch (ContractException e) {
				System.out.println("Unable to get network/contract and execute query : " + e.toString() + ", Response : " + e.getProposalResponses().toString() );
				throw new javax.ws.rs.ServiceUnavailableException();
			}
		} 
		catch (IOException e) {
			System.out.println("Current working dir: " + currentDir);
			System.out.println(
					"Unable to find config or wallet - please check the wallet directory and connection json");
			throw new javax.ws.rs.NotFoundException();
		}
		catch (Exception e)		
		{
			System.out.println(e.toString());
			throw new javax.ws.rs.ServiceUnavailableException();
		}
    }
    
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update Asset Information", description = "Requires the Asset ID and the New Value to be provided")
	@javax.ws.rs.Path("updateAsset")
    public Response deleteAsset(@QueryParam("assetId") String assetId) {
        try {
			currentDir = new java.io.File(".").getCanonicalPath();
			byte[] response = new String("Null").getBytes() ;

			Path walletPath = Paths.get(pathRoot.toString(), "wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);
			Path networkConfigPath = Paths.get(pathRoot + connectionFile);

			Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "sachi").networkConfig(networkConfigPath).discovery(true);
			try(Gateway gateway = builder.connect()) {
				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				org.hyperledger.fabric.gateway.Contract contract = network.getContract("asset");
				org.hyperledger.fabric.gateway.Transaction transaction = contract.createTransaction("deleteAsset");
				response = transaction.submit(assetId);
				return Response.status(Response.Status.OK).entity(new String(response)).build();
			} catch (ContractException e) {
				System.out.println("Unable to get network/contract and execute query : " + e.toString() + ", Response : " + e.getProposalResponses().toString() );
				throw new javax.ws.rs.ServiceUnavailableException();
			}
		} 
		catch (IOException e) {
			System.out.println("Current working dir: " + currentDir);
			System.out.println(
					"Unable to find config or wallet - please check the wallet directory and connection json");
			throw new javax.ws.rs.NotFoundException();
		}
		catch (Exception e)		
		{
			System.out.println(e.toString());
			throw new javax.ws.rs.ServiceUnavailableException();
		}
    }
}
