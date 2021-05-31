/*
 * SPDX-License-Identifier: Apache-2.0
 */
package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import static java.nio.charset.StandardCharsets.UTF_8;

@Contract(name = "AssetContract",
    info = @Info(title = "Asset contract",
                description = "My Smart Contract",
                version = "0.0.1",
                license =
                        @License(name = "Apache-2.0",
                                url = ""),
                                contact =  @Contact(email = "HyperledgerFab_UWB_Chaincode@example.com",
                                                name = "HyperledgerFab_UWB_Chaincode",
                                                url = "http://HyperledgerFab_UWB_Chaincode.me")))
@Default
public class AssetContract implements ContractInterface {
    public  AssetContract() {

    }
    @Transaction()
    public boolean assetExists(Context ctx, String assetId) {
        byte[] buffer = ctx.getStub().getState(assetId);
        return (buffer != null && buffer.length > 0);
    }

    @Transaction()
    public void createAsset(Context ctx, String assetId, String value) {
        boolean exists = assetExists(ctx,assetId);
        if (exists) {
            throw new RuntimeException("The asset "+assetId+" already exists");
        }
        Asset asset = new Asset();
        asset.setValue(value);
        ctx.getStub().putState(assetId, asset.toJSONString().getBytes(UTF_8));
    }

    @Transaction()
    public Asset readAsset(Context ctx, String assetId) {
        boolean exists = assetExists(ctx,assetId);
        if (!exists) {
            throw new RuntimeException("The asset "+assetId+" does not exist");
        }

        Asset newAsset = Asset.fromJSONString(new String(ctx.getStub().getState(assetId),UTF_8));
        return newAsset;
    }

    @Transaction()
    public void updateAsset(Context ctx, String assetId, String newValue) {
        boolean exists = assetExists(ctx,assetId);
        if (!exists) {
            throw new RuntimeException("The asset "+assetId+" does not exist");
        }
        Asset asset = new Asset();
        asset.setValue(newValue);

        ctx.getStub().putState(assetId, asset.toJSONString().getBytes(UTF_8));
    }

    @Transaction()
    public void deleteAsset(Context ctx, String assetId) {
        boolean exists = assetExists(ctx,assetId);
        if (!exists) {
            throw new RuntimeException("The asset "+assetId+" does not exist");
        }
        ctx.getStub().delState(assetId);
    }

}
