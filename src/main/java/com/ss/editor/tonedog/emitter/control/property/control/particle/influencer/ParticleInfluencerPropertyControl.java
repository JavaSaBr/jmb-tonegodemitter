package com.ss.editor.tonedog.emitter.control.property.control.particle.influencer;

import com.ss.editor.model.undo.editor.ModelChangeConsumer;
import com.ss.editor.tonedog.emitter.control.operation.ParticleInfluencerPropertyOperation;
import com.ss.editor.ui.component.editor.impl.model.ModelFileEditor;
import com.ss.editor.ui.control.property.PropertyControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tonegod.emitter.influencers.ParticleInfluencer;

/**
 * The base implementation of the property control for the {@link ModelFileEditor}.
 *
 * @param <D> the type parameter
 * @param <T> the type parameter
 * @author JavaSaBr
 */
public class ParticleInfluencerPropertyControl<D extends ParticleInfluencer, T> extends
        PropertyControl<ModelChangeConsumer, D, T> {

    /**
     * New change handler six object consumer.
     *
     * @param <D>    the type parameter
     * @param <T>    the type parameter
     * @param parent the parent
     * @return the six object consumer
     */
    public static <D extends ParticleInfluencer, T> @NotNull ChangeHandler<ModelChangeConsumer, D, T> newChangeHandler(@NotNull final Object parent) {
        return (changeConsumer, object, propName, newValue, oldValue, handler) -> {

            final ParticleInfluencerPropertyOperation<D, T> operation =
                    new ParticleInfluencerPropertyOperation<>(object, parent, propName, newValue, oldValue);
            operation.setApplyHandler(handler);

            changeConsumer.execute(operation);
        };
    }

    public ParticleInfluencerPropertyControl(@Nullable final T propertyValue, @NotNull final String propertyName,
                                             @NotNull final ModelChangeConsumer changeConsumer,
                                             @NotNull final Object parent) {
        super(propertyValue, propertyName, changeConsumer, newChangeHandler(parent));
    }
}
