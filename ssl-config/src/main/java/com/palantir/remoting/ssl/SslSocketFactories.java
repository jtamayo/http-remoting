/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.remoting.ssl;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Utility functions for creating {@link SSLSocketFactory}s
 * that are configured with Java trust stores and key stores.
 */
public final class SslSocketFactories {
    private SslSocketFactories() {}

    /**
     * Create an {@link SSLSocketFactory} from the provided configuration.
     *
     * @param config an {@link SslConfiguration} describing the trust store and key store configuration
     * @return an {@link SSLSocketFactory} according to the input configuration
     */
    public static SSLSocketFactory createSslSocketFactory(SslConfiguration config) {
        SSLContext sslContext = createSslContext(config);
        return sslContext.getSocketFactory();
    }

    /**
     * Create an {@link SSLContext} initialized from the provided configuration.
     *
     * @param config an {@link SslConfiguration} describing the trust store and key store configuration
     * @return an {@link SSLContext} according to the input configuration
     */
    public static SSLContext createSslContext(SslConfiguration config) {
        TrustManager[] trustManagers = createTrustManagerFactory(
                config.trustStorePath(),
                config.trustStoreType()).getTrustManagers();

        KeyManager[] keyManagers = null;
        if (config.keyStorePath().isPresent()) {
            keyManagers = createKeyManagerFactory(
                    config.keyStorePath().get(),
                    config.keyStorePassword().get(),
                    config.keyStoreType(),
                    config.keyStoreKeyAlias()).getKeyManagers();
        }

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, null);
            return sslContext;
        } catch (GeneralSecurityException e) {
            throw Throwables.propagate(e);
        }
    }

    private static TrustManagerFactory createTrustManagerFactory(
            Path trustStorePath,
            SslConfiguration.StoreType trustStoreType) {

        KeyStore keyStore;
        switch (trustStoreType) {
            case JKS:
            case PKCS12:
                keyStore = KeyStores.loadKeyStore(trustStoreType.name(), trustStorePath, Optional.<String>absent());
                break;
            case PEM:
                keyStore = KeyStores.createTrustStoreFromCertificates(trustStorePath);
                break;
            default:
                throw new IllegalStateException("Unrecognized trust store type: " + trustStoreType);
        }

        try {
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            return trustManagerFactory;
        } catch (GeneralSecurityException e) {
            throw Throwables.propagate(e);
        }
    }

    private static KeyManagerFactory createKeyManagerFactory(
            Path keyStorePath,
            String keyStorePassword,
            SslConfiguration.StoreType keyStoreType,
            Optional<String> keyStoreKeyAlias) {

        KeyStore keyStore;
        switch (keyStoreType) {
            case JKS:
            case PKCS12:
                keyStore = KeyStores.loadKeyStore(keyStoreType.name(), keyStorePath, Optional.of(keyStorePassword));
                break;
            case PEM:
                throw new IllegalStateException("PEM is not supported as a key store type");
            default:
                throw new IllegalStateException("Unrecognized key store type: " + keyStoreType);
        }

        if (keyStoreKeyAlias.isPresent()) {
            // default KeyManagerFactory does not support referencing key by alias, so
            // if a key with a specific alias is desired, construct a new key store that
            // contains only the key and certificate with that alias
            keyStore = KeyStores.newKeyStoreWithEntry(keyStore, keyStorePassword, keyStoreKeyAlias.get());
        }

        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

            return keyManagerFactory;
        } catch (GeneralSecurityException e) {
            throw Throwables.propagate(e);
        }
    }

}
