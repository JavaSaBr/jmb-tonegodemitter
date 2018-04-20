package com.ss.editor.tonedog.emitter.control.property.builder;

import com.jme3.math.Vector2f;
import com.ss.editor.Messages;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ModelChangeConsumer;
import com.ss.editor.tonedog.emitter.control.property.control.particle.ParticleEmitterMaterialPropertyControl;
import com.ss.editor.tonedog.emitter.control.property.control.particle.ParticleEmitterSpriteCountModelPropertyControl;
import com.ss.editor.ui.control.property.PropertyControl;
import com.ss.editor.ui.control.property.builder.PropertyBuilder;
import com.ss.editor.ui.control.property.builder.impl.AbstractPropertyBuilder;
import com.ss.editor.ui.control.property.builder.impl.SpatialPropertyBuilder;
import com.ss.editor.ui.control.property.impl.*;
import com.ss.rlib.fx.util.FxUtils;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tonegod.emitter.BillboardMode;
import tonegod.emitter.EmissionPoint;
import tonegod.emitter.EmitterMesh.DirectionType;
import tonegod.emitter.ParticleEmitterNode;
import tonegod.emitter.material.ParticlesMaterial;

import java.util.function.BiConsumer;

/**
 * The implementation of the {@link PropertyBuilder} to build property controls for {@link ParticleEmitterNode}.
 *
 * @author JavaSaBr
 */
public class ParticleEmitterPropertyBuilder extends AbstractPropertyBuilder<ModelChangeConsumer> {

    public static final int PRIORITY = SpatialPropertyBuilder.PRIORITY + 1;

    @NotNull
    private static final DirectionType[] DIRECTION_TYPES = DirectionType.values();

    @NotNull
    private static final EmissionPoint[] PARTICLE_EMISSION_POINTS = EmissionPoint.values();

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Boolean> FOLLOW_EMITTER_HANDLER = (emitter, result) -> {
        emitter.setParticlesFollowEmitter(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Boolean> VELOCITY_STRETCHING_HANDLER = (emitter, result) -> {
        emitter.setVelocityStretching(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Boolean> RANDOM_EMISSION_POINT_HANDLER = (emitter, result) -> {
        emitter.setRandomEmissionPoint(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Boolean> SEQUENTIAL_EMISSION_FACE_HANDLER = (emitter, result) -> {
        emitter.setSequentialEmissionFace(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, DirectionType> DIRECTION_TYPE_HANDLER = (emitter, result) -> {
        emitter.setDirectionType(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Boolean> SEQUENTIAL_SKIP_PATTERN_HANDLER = (emitter, result) -> {
        emitter.setSequentialSkipPattern(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, EmissionPoint> EMISSION_POINT_HANDLER = (emitter, result) -> {
        emitter.setEmissionPoint(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Float> EMISSIONS_PER_SECOND_HANDLER = (emitter, result) -> {
        emitter.setEmissionsPerSecond(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Integer> PARTICLES_PER_EMISSION_HANDLER = (emitter, result) -> {
        emitter.setParticlesPerEmission(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Vector2f> FORCE_MIN_MAX_HANDLER = (emitter, result) -> {
        emitter.setForceMinMax(result);
        emitter.killAllParticles();
    };

    @NotNull
    private static final BiConsumer<ParticleEmitterNode, Vector2f> LIFE_MIN_MAX_HANDLER = (emitter, result) -> {
        emitter.setLifeMinMax(result);
        emitter.killAllParticles();
    };


    @NotNull
    private static final PropertyBuilder INSTANCE = new ParticleEmitterPropertyBuilder();

    @FromAnyThread
    public static @NotNull PropertyBuilder getInstance() {
        return INSTANCE;
    }

    private ParticleEmitterPropertyBuilder() {
        super(ModelChangeConsumer.class);
    }

    @Override
    @FxThread
    protected void buildForImpl(
            @NotNull Object object,
            @Nullable Object parent,
            @NotNull VBox container,
            @NotNull ModelChangeConsumer changeConsumer
    ) {

        if (!(object instanceof ParticleEmitterNode)) {
            return;
        }

        buildFor(container, changeConsumer, (ParticleEmitterNode) object);
        buildSplitLine(container);
    }

    @FxThread
    private void buildFor(
            @NotNull VBox container,
            @NotNull ModelChangeConsumer changeConsumer,
            @NotNull ParticleEmitterNode emitterNode
    ) {

        var particlesMaterial = emitterNode.getParticlesMaterial();

        var testEmitter = emitterNode.isEnabledTestEmitter();
        var testParticles = emitterNode.isEnabledTestParticles();
        var enabled = emitterNode.isEnabled();
        var randomEmissionPoint = emitterNode.isRandomEmissionPoint();
        var sequentialEmissionFace = emitterNode.isSequentialEmissionFace();
        var skipPattern = emitterNode.isSequentialSkipPattern();
        var particlesFollowEmitter = emitterNode.isParticlesFollowEmitter();
        var velocityStretching = emitterNode.isVelocityStretching();

        var maxParticles = emitterNode.getMaxParticles();
        var particlesPerEmission = emitterNode.getParticlesPerEmission();

        var emissionsPerSecond = emitterNode.getEmissionsPerSecond();
        var emitterLife = emitterNode.getEmitterLife();
        var emitterDelay = emitterNode.getEmitterDelay();
        var stretchFactor = emitterNode.getVelocityStretchFactor();

        var directionType = emitterNode.getDirectionType();
        var billboardMode = emitterNode.getBillboardMode();
        var emissionPoint = emitterNode.getEmissionPoint();

        var forceMinMax = emitterNode.getForceMinMax();
        var lifeMinMax = emitterNode.getLifeMinMax();
        var spriteCount = emitterNode.getSpriteCount();

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> testEmitterControl =
                new BooleanPropertyControl<>(testEmitter, Messages.MODEL_PROPERTY_IS_TEST_MODE, changeConsumer);
        testEmitterControl.setApplyHandler(ParticleEmitterNode::setEnabledTestEmitter);
        testEmitterControl.setSyncHandler(ParticleEmitterNode::isEnabledTestEmitter);
        testEmitterControl.setEditObject(emitterNode);

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> testParticlesControl =
                new BooleanPropertyControl<>(testParticles, Messages.MODEL_PROPERTY_IS_TEST_PARTICLES, changeConsumer);
        testParticlesControl.setApplyHandler(ParticleEmitterNode::setEnabledTestParticles);
        testParticlesControl.setSyncHandler(ParticleEmitterNode::isEnabledTestParticles);
        testParticlesControl.setEditObject(emitterNode);

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> enableControl =
                new BooleanPropertyControl<>(enabled, Messages.MODEL_PROPERTY_IS_ENABLED, changeConsumer);
        enableControl.setApplyHandler(ParticleEmitterNode::setEnabled);
        enableControl.setSyncHandler(ParticleEmitterNode::isEnabled);
        enableControl.setEditObject(emitterNode);

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> particlesFollowEmitControl =
                new BooleanPropertyControl<>(particlesFollowEmitter, Messages.MODEL_PROPERTY_IS_FOLLOW_EMITTER, changeConsumer);
        particlesFollowEmitControl.setApplyHandler(FOLLOW_EMITTER_HANDLER);
        particlesFollowEmitControl.setSyncHandler(ParticleEmitterNode::isParticlesFollowEmitter);
        particlesFollowEmitControl.setEditObject(emitterNode);

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> particlesStretchingControl =
                new BooleanPropertyControl<>(velocityStretching, Messages.MODEL_PROPERTY_STRETCHING, changeConsumer);
        particlesStretchingControl.setApplyHandler(VELOCITY_STRETCHING_HANDLER);
        particlesStretchingControl.setSyncHandler(ParticleEmitterNode::isVelocityStretching);
        particlesStretchingControl.setEditObject(emitterNode);

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> randomPointControl =
                new BooleanPropertyControl<>(randomEmissionPoint, Messages.MODEL_PROPERTY_IS_RANDOM_POINT, changeConsumer);
        randomPointControl.setApplyHandler(RANDOM_EMISSION_POINT_HANDLER);
        randomPointControl.setSyncHandler(ParticleEmitterNode::isRandomEmissionPoint);
        randomPointControl.setEditObject(emitterNode);

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> sequentialFaceControl =
                new BooleanPropertyControl<>(sequentialEmissionFace, Messages.MODEL_PROPERTY_IS_SEQUENTIAL_FACE, changeConsumer);
        sequentialFaceControl.setApplyHandler(SEQUENTIAL_EMISSION_FACE_HANDLER);
        sequentialFaceControl.setSyncHandler(ParticleEmitterNode::isSequentialEmissionFace);
        sequentialFaceControl.setEditObject(emitterNode);

        BooleanPropertyControl<ModelChangeConsumer, ParticleEmitterNode> skipPatternControl =
                new BooleanPropertyControl<>(skipPattern, Messages.MODEL_PROPERTY_IS_SKIP_PATTERN, changeConsumer);
        skipPatternControl.setApplyHandler(SEQUENTIAL_SKIP_PATTERN_HANDLER);
        skipPatternControl.setSyncHandler(ParticleEmitterNode::isSequentialSkipPattern);
        skipPatternControl.setEditObject(emitterNode);

        EnumPropertyControl<ModelChangeConsumer, ParticleEmitterNode, DirectionType> directionTypeControl =
                new EnumPropertyControl<>(directionType, Messages.MODEL_PROPERTY_DIRECTION_TYPE, changeConsumer, DIRECTION_TYPES);
        directionTypeControl.setApplyHandler(DIRECTION_TYPE_HANDLER);
        directionTypeControl.setSyncHandler(ParticleEmitterNode::getDirectionType);
        directionTypeControl.setEditObject(emitterNode);

        EnumPropertyControl<ModelChangeConsumer, ParticleEmitterNode, EmissionPoint> emissionPointControl =
                new EnumPropertyControl<>(emissionPoint, Messages.MODEL_PROPERTY_EMISSION_POINT, changeConsumer, PARTICLE_EMISSION_POINTS);
        emissionPointControl.setApplyHandler(EMISSION_POINT_HANDLER);
        emissionPointControl.setSyncHandler(ParticleEmitterNode::getEmissionPoint);
        emissionPointControl.setEditObject(emitterNode);

        EnumPropertyControl<ModelChangeConsumer, ParticleEmitterNode, BillboardMode> billboardModeControl =
                new EnumPropertyControl<>(billboardMode, Messages.MODEL_PROPERTY_BILLBOARD, changeConsumer, BillboardMode.values());
        billboardModeControl.setApplyHandler(ParticleEmitterNode::setBillboardMode);
        billboardModeControl.setSyncHandler(ParticleEmitterNode::getBillboardMode);
        billboardModeControl.setEditObject(emitterNode);

        IntegerPropertyControl<ModelChangeConsumer, ParticleEmitterNode> maxParticlesControl =
                new IntegerPropertyControl<>(maxParticles, Messages.MODEL_PROPERTY_MAX_PARTICLES, changeConsumer);
        maxParticlesControl.setApplyHandler(ParticleEmitterNode::setMaxParticles);
        maxParticlesControl.setSyncHandler(ParticleEmitterNode::getMaxParticles);
        maxParticlesControl.setEditObject(emitterNode);

        FloatPropertyControl<ModelChangeConsumer, ParticleEmitterNode> emissionPerSecControl =
                new FloatPropertyControl<>(emissionsPerSecond, Messages.MODEL_PROPERTY_EMISSION_PER_SECOND, changeConsumer);
        emissionPerSecControl.setApplyHandler(EMISSIONS_PER_SECOND_HANDLER);
        emissionPerSecControl.setSyncHandler(ParticleEmitterNode::getEmissionsPerSecond);
        emissionPerSecControl.setMinMax(0.1F, Integer.MAX_VALUE);
        emissionPerSecControl.setScrollPower(3F);
        emissionPerSecControl.setEditObject(emitterNode);

        IntegerPropertyControl<ModelChangeConsumer, ParticleEmitterNode> particlesPerEmissionControl =
                new IntegerPropertyControl<>(particlesPerEmission, Messages.MODEL_PROPERTY_PARTICLES_PER_SECOND, changeConsumer);
        particlesPerEmissionControl.setApplyHandler(PARTICLES_PER_EMISSION_HANDLER);
        particlesPerEmissionControl.setSyncHandler(ParticleEmitterNode::getParticlesPerEmission);
        particlesPerEmissionControl.setEditObject(emitterNode);

        FloatPropertyControl<ModelChangeConsumer, ParticleEmitterNode> emitterLifeControl =
                new FloatPropertyControl<>(emitterLife, Messages.MODEL_PROPERTY_EMITTER_LIFE, changeConsumer);
        emitterLifeControl.setApplyHandler(ParticleEmitterNode::setEmitterLife);
        emitterLifeControl.setSyncHandler(ParticleEmitterNode::getEmitterLife);
        emitterLifeControl.setEditObject(emitterNode);

        FloatPropertyControl<ModelChangeConsumer, ParticleEmitterNode> emitterDelayControl =
                new FloatPropertyControl<>(emitterDelay, Messages.MODEL_PROPERTY_EMITTER_DELAY, changeConsumer);
        emitterDelayControl.setApplyHandler(ParticleEmitterNode::setEmitterDelay);
        emitterDelayControl.setSyncHandler(ParticleEmitterNode::getEmitterDelay);
        emitterDelayControl.setEditObject(emitterNode);

        FloatPropertyControl<ModelChangeConsumer, ParticleEmitterNode> magnitudeControl =
                new FloatPropertyControl<>(stretchFactor, Messages.MODEL_PROPERTY_MAGNITUDE, changeConsumer);
        magnitudeControl.setApplyHandler(ParticleEmitterNode::setVelocityStretchFactor);
        magnitudeControl.setSyncHandler(ParticleEmitterNode::getVelocityStretchFactor);
        magnitudeControl.setEditObject(emitterNode);

        PropertyControl<ModelChangeConsumer, ParticleEmitterNode, ParticlesMaterial> materialControl =
                new ParticleEmitterMaterialPropertyControl(particlesMaterial, Messages.MODEL_PROPERTY_MATERIAL, changeConsumer);
        materialControl.setApplyHandler(ParticleEmitterNode::setParticlesMaterial);
        materialControl.setSyncHandler(ParticleEmitterNode::getParticlesMaterial);
        materialControl.setEditObject(emitterNode);

        MinMaxPropertyControl<ModelChangeConsumer, ParticleEmitterNode> forceMinMaxControl =
                new MinMaxPropertyControl<>(forceMinMax, Messages.MODEL_PROPERTY_INITIAL_FORCE, changeConsumer);
        forceMinMaxControl.setApplyHandler(FORCE_MIN_MAX_HANDLER);
        forceMinMaxControl.setSyncHandler(ParticleEmitterNode::getForceMinMax);
        forceMinMaxControl.setEditObject(emitterNode);

        MinMaxPropertyControl<ModelChangeConsumer, ParticleEmitterNode> lifeMinMaxControl =
                new MinMaxPropertyControl<>(lifeMinMax, Messages.MODEL_PROPERTY_LIFE, changeConsumer);
        lifeMinMaxControl.setApplyHandler(LIFE_MIN_MAX_HANDLER);
        lifeMinMaxControl.setSyncHandler(ParticleEmitterNode::getLifeMinMax);
        lifeMinMaxControl.setEditObject(emitterNode);

        ParticleEmitterSpriteCountModelPropertyControl spriteCountControl =
                new ParticleEmitterSpriteCountModelPropertyControl(spriteCount, Messages.MODEL_PROPERTY_SPRITE_COUNT, changeConsumer);
        spriteCountControl.setApplyHandler(ParticleEmitterNode::setSpriteCount);
        spriteCountControl.setSyncHandler(ParticleEmitterNode::getSpriteCount);
        spriteCountControl.setEditObject(emitterNode);

        FxUtils.addChild(container, enableControl, testEmitterControl, testParticlesControl, randomPointControl,
                sequentialFaceControl, skipPatternControl, particlesFollowEmitControl, particlesStretchingControl,
                directionTypeControl, emissionPointControl, billboardModeControl, maxParticlesControl, 
                emissionPerSecControl, particlesPerEmissionControl, emitterLifeControl, emitterDelayControl,
                magnitudeControl, spriteCountControl, forceMinMaxControl, lifeMinMaxControl);
        
        buildSplitLine(container);

        FxUtils.addChild(container, materialControl);
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
