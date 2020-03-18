package com.company;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class CatalogUtil {

    public static void save(Catalog catalog) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            oos.writeObject(catalog);
        }
    }

    public static Catalog load(String path) throws InvalidCatalogException, IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path)
        )) {
            return (Catalog)ois.readObject();
        }
    }

    public static void view(Document doc) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        URI uri = new URI(doc.getLocation());
        desktop.browse(uri);
        //… browse or open, depending of the location type
    }
}