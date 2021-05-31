/*
 * SPDX-License-Identifier: Apache-2.0
 */

package Hyperledger.rest.api.openapi;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.owlike.genson.Genson;

@Schema(name="Asset")
public class Asset {

    private final static Genson genson = new Genson();

    private String value;

    public Asset(){
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toJSONString() {
        return genson.serialize(this).toString();
    }

    public static Asset fromJSONString(String json) {
        Asset asset = genson.deserialize(json, Asset.class);
        return asset;
    }
}
