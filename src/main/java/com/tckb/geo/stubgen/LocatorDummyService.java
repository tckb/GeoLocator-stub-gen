/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tckb.geo.stubgen;

import retrofit.client.Response;
import retrofit.http.GET;

/**
 *
 * @author tckb <chandra.tungathurthi@rwth-aachen.de>
 */
public interface LocatorDummyService {

    @GET("/getDummyDevice")
    public Response getDeviceJSON();

    @GET("/getDummyLocation")
    public Response geLocationJSON();

    @GET("/getDummyCluster")
    public Response getClusterJSON();

}
