package com.zuryx.app;

import java.util.Collection;

public interface IImageDataStoreService {
    void saveImagesOnDatabase(Collection<String> urls);
}
