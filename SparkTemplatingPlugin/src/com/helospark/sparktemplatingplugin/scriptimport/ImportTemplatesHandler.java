package com.helospark.sparktemplatingplugin.scriptimport;

import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.helospark.sparktemplatingplugin.DiContainer;
import com.helospark.sparktemplatingplugin.scriptimport.job.ImportJob;
import com.helospark.sparktemplatingplugin.scriptimport.job.ImportJobWorker;

public class ImportTemplatesHandler extends AbstractHandler {
    private ImportJobWorker importJobWorker;

    public ImportTemplatesHandler() {
        this.importJobWorker = DiContainer.getDependency(ImportJobWorker.class);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Optional<String> result = createOpenDialog();
        if (result.isPresent()) {
            ImportJob importJob = new ImportJob(importJobWorker, result.get());
            importJob.schedule();
        }
        return null;
    }

    private Optional<String> createOpenDialog() {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        FileDialog dialog = new FileDialog(shell, SWT.OPEN);
        dialog.setFilterExtensions(new String[] { "*.zip" });
        String resultFileName = dialog.open();
        return Optional.ofNullable(resultFileName);
    }

}
