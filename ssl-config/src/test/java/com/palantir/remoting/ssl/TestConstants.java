/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.remoting.ssl;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test constants for trust stores and key stores used in unit tests.
 */
final class TestConstants {

    static final Path CA_TRUST_STORE_PATH = Paths.get("src", "test", "resources", "testCA", "testCATrustStore.jks");
    static final Path CA_DER_CERT_PATH = Paths.get("src", "test", "resources", "testCA", "testCA.der");
    static final Path CA_PEM_CERT_PATH = Paths.get("src", "test", "resources", "testCA", "testCA.crt");
    static final SslConfiguration.StoreType CA_TRUST_STORE_TYPE = SslConfiguration.StoreType.JKS;

    static final Path CA_CRL_PATH = Paths.get("src", "test", "resources", "crl.pem");

    static final Path SERVER_CERT_PEM_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "testServer",
            "testServer.crt");

    static final Path SERVER_KEY_STORE_JKS_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "testServer",
            "testServerKeyStore.jks");
    static final SslConfiguration.StoreType SERVER_KEY_STORE_JKS_TYPE = SslConfiguration.StoreType.JKS;
    static final String SERVER_KEY_STORE_JKS_PASSWORD = "serverStore";
    static final Path SERVER_KEY_PEM_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "testServer",
            "testServer.key");

    static final Path SERVER_KEY_STORE_P12_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "testServer",
            "testServerKeyStore.p12");
    static final SslConfiguration.StoreType SERVER_KEY_STORE_P12_TYPE = SslConfiguration.StoreType.PKCS12;
    static final String SERVER_KEY_STORE_P12_PASSWORD = "testServer";

    static final Path CLIENT_CERT_PEM_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "testClient",
            "testClient.crt");

    static final Path CLIENT_KEY_STORE_JKS_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "testClient",
            "testClientKeyStore.jks");
    static final String CLIENT_KEY_STORE_JKS_PASSWORD = "clientStore";

    static final Path CLIENT_KEY_STORE_P12_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "testClient",
            "testClientKeyStore.p12");
    static final SslConfiguration.StoreType CLIENT_KEY_STORE_P12_TYPE = SslConfiguration.StoreType.PKCS12;
    static final String CLIENT_KEY_STORE_P12_PASSWORD = "testClient";

    static final Path MULTIPLE_KEY_STORE_JKS_PATH = Paths.get(
            "src",
            "test",
            "resources",
            "multiple.jks");
    static final String MULTIPLE_KEY_STORE_JKS_PASSWORD = "multiple";
    static final String MULTIPLE_KEY_STORE_CLIENT_ALIAS = "testClient";
    static final String MULTIPLE_KEY_STORE_SERVER_ALIAS = "testServer";

    private TestConstants() {}

}
