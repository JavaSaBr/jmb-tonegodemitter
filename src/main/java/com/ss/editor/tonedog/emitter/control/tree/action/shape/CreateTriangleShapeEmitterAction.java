package com.ss.editor.tonedog.emitter.control.tree.action.shape;

import static com.ss.editor.extension.property.EditablePropertyType.FLOAT;
import com.jme3.scene.Mesh;
import com.ss.editor.Messages;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.plugin.api.property.PropertyDefinition;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.common.util.VarTable;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tonegod.emitter.ParticleEmitterNode;
import tonegod.emitter.shapes.TriangleEmitterShape;

/**
 * The action to switch an emitter shape of the {@link ParticleEmitterNode} to a {@link TriangleEmitterShape}.
 *
 * @author JavaSaBr
 */
public class CreateTriangleShapeEmitterAction extends AbstractCreateShapeEmitterAction {

    @NotNull
    private static final String PROPERTY_SIZE = "size";

    public CreateTriangleShapeEmitterAction(@NotNull NodeTree<?> nodeTree, @NotNull TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    @FxThread
    protected @Nullable Image getIcon() {
        return Icons.TRIANGLE_16;
    }

    @Override
    @FxThread
    protected @NotNull String getName() {
        return Messages.MODEL_NODE_TREE_ACTION_PARTICLE_EMITTER_TRIANGLE_SHAPE;
    }

    @Override
    @FxThread
    protected @NotNull Array<PropertyDefinition> getPropertyDefinitions() {

        var definitions = ArrayFactory.<PropertyDefinition>newArray(PropertyDefinition.class);
        definitions.add(new PropertyDefinition(FLOAT, Messages.MODEL_PROPERTY_SIZE, PROPERTY_SIZE, 1F));

        return definitions;
    }

    @Override
    @FxThread
    protected @NotNull String getDialogTitle() {
        return Messages.CREATE_PARTICLE_EMITTER_TRIANGLE_SHAPE_DIALOG_TITLE;
    }

    @Override
    @FxThread
    protected @NotNull Mesh createMesh(@NotNull VarTable vars) {
        var size = vars.getFloat(PROPERTY_SIZE);
        return new TriangleEmitterShape(size);
    }
}
