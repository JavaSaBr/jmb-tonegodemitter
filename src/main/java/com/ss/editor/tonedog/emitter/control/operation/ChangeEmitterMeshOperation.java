package com.ss.editor.tonedog.emitter.control.operation;

import com.jme3.scene.Mesh;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.JmeThread;
import com.ss.editor.model.undo.editor.ModelChangeConsumer;
import com.ss.editor.model.undo.impl.AbstractEditorOperation;
import org.jetbrains.annotations.NotNull;
import tonegod.emitter.ParticleEmitterNode;

/**
 * The implementation of the {@link AbstractEditorOperation} for changing a shape in the {@link ParticleEmitterNode}.
 *
 * @author JavaSaBr.
 */
public class ChangeEmitterMeshOperation extends AbstractEditorOperation<ModelChangeConsumer> {

    /**
     * The emitter node.
     */
    @NotNull
    private final ParticleEmitterNode emitterNode;

    /**
     * The prev shape.
     */
    @NotNull
    private volatile Mesh prevShape;

    public ChangeEmitterMeshOperation(@NotNull Mesh newShape, @NotNull ParticleEmitterNode emitterNode) {
        this.prevShape = newShape;
        this.emitterNode = emitterNode;
    }

    @Override
    @FxThread
    protected void redoImpl(@NotNull ModelChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> switchShape(editor));
    }

    @JmeThread
    private void switchShape(@NotNull ModelChangeConsumer editor) {

        var emitterMesh = emitterNode.getEmitterShape();
        var newShape = prevShape;
        prevShape = emitterMesh.getMesh();
        emitterNode.changeEmitterShapeMesh(newShape);

        EXECUTOR_MANAGER.addFxTask(() -> editor.notifyFxReplaced(emitterNode, emitterMesh,
                emitterMesh, true, true));
    }

    @Override
    @FxThread
    protected void undoImpl(@NotNull ModelChangeConsumer editor) {
        EXECUTOR_MANAGER.addJmeTask(() -> switchShape(editor));
    }
}
