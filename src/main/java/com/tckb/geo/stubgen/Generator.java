/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tckb.geo.stubgen;

import com.sun.codemodel.JCodeModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.NoopAnnotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import retrofit.RestAdapter;
import retrofit.client.Response;

/**
 *
 * @author tckb <chandra.tungathurthi@rwth-aachen.de>
 */
public class Generator {

    private static String serviceEndPoint;
    private static String outDir;
    private static LocatorDummyService remoteService;
    private static String deviceJson;
    private static String clusterJson;
    private static String locationJson;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            serviceEndPoint = args[0];
            outDir = args[1];
        } else {
            serviceEndPoint = "http://webapps-tckb.rhcloud.com/GeoLocator/service/geo/";
            outDir = "gen-stubs";
        }
        remoteService = new RestAdapter.Builder().setEndpoint(serviceEndPoint).build().create(LocatorDummyService.class);
        generateStubs();
    }

    /**
     * Generates the java stubs for the POJOs used for REST web service
     */
    public static void generateStubs() {
        if (new File(outDir).mkdir()) {
            try {
                getRawJsonFromRemote();
                Logger.getAnonymousLogger().info("Generating stubs...");

                JCodeModel model = new JCodeModel();
                SchemaMapper mapper = new SchemaMapper(new RuleFactory(new DefaultGenerationConfig() {
                    @Override
                    public SourceType getSourceType() {
                        return SourceType.JSON;
                    }

                    @Override
                    public boolean isUseCommonsLang3() {
                        return false;
                    }

                    @Override
                    public boolean isIncludeHashcodeAndEquals() {
                        return false;
                    }

                    @Override
                    public boolean isIncludeToString() {
                        return false;
                    }

                    @Override
                    public boolean isIncludeJsr303Annotations() {
                        return false;
                    }

                }, new NoopAnnotator(), new SchemaStore()), new SchemaGenerator());

                mapper.generate(model, "Device", "com.tckb.geo.stubs", new URL("file://" + deviceJson));
                model.build(new File(outDir));
                mapper.generate(model, "Cluster", "com.tckb.geo.stubs", new URL("file://" + clusterJson));
                model.build(new File(outDir));

            } catch (IOException ex) {

                Logger.getAnonymousLogger().log(Level.SEVERE, "Failed ! {0}", ex.getLocalizedMessage());
            }
            Logger.getAnonymousLogger().info("Finished");

        } else {
            Logger.getAnonymousLogger().severe("Cannot create output directory!");

        }

    }

    /**
     * Gets the json from the webservice and saves it in local as JSON file
     *
     * @throws IOException
     */
    public static void getRawJsonFromRemote() throws IOException {

        deviceJson = createJSONFile(getRawData(remoteService.getDeviceJSON()), "resp_device");
        clusterJson = createJSONFile(getRawData(remoteService.getClusterJSON()), "resp_cluster");
//        locationJson = createJSONFile(getRawData(remoteService.geLocationJSON()), "resp_loc.json");

        Logger.getAnonymousLogger().info("Parsing raw data...");

    }

    /**
     * Retrieves the raw data response from the webservice
     *
     * @param r
     * @return
     * @throws IOException
     */
    public static String getRawData(Response r) throws IOException {
        Logger.getAnonymousLogger().log(Level.INFO, "Retrieving raw data from remote url: {0}", r.getUrl());
        if (r.getStatus() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(r.getBody().in()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Server returned {0} status while retrieving{1} !", new Object[]{String.valueOf(r.getStatus()), r.getUrl()});
            return "_NO_CONTENT_";
        }
    }

    /**
     * Create a file with 'fileName' with 'rawData' contents
     *
     * @param rawData
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    public static String createJSONFile(String rawData, String fileName) throws IOException {
        File file = File.createTempFile(fileName, ".json");
        try (FileWriter fr = new FileWriter(file)) {
            fr.write(rawData);
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error while creating file: ", ex);
            return "";
        }
       
        return file.getAbsolutePath();
    }

}
