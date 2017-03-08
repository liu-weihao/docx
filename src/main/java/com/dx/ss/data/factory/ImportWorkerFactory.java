package com.dx.ss.data.factory;

import com.dx.ss.data.worker.DocumentWorker;
import com.dx.ss.data.worker.ImportWorker;

public class ImportWorkerFactory implements DocumentWorkerFactory {

    public DocumentWorker createWorker() {
        
        return new ImportWorker();
    }

}
