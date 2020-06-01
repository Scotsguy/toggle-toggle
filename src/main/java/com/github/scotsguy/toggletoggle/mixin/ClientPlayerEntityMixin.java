package com.github.scotsguy.toggletoggle.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
@Environment(EnvType.CLIENT)
public abstract class ClientPlayerEntityMixin {
    @Shadow
    public Input input;

    @Unique
    private int ticksSinceLastSneakInput;

    @Inject(method = "tickMovement()V", at = @At(value = "HEAD"))
    private void toggleToggleSneaking(CallbackInfo ci) {
        KeyboardInput input = (KeyboardInput) this.input;
        GameOptions settings = ((KeyboardInputAccess) input).getSettings();
        boolean userPressingSneakKey = InputUtil.isKeyPressed(
                MinecraftClient.getInstance().getWindow().getHandle(),
                ((KeyBindingAccess) settings.keySneak).getBoundKey().getCode());

        if (userPressingSneakKey) {
            if (this.ticksSinceLastSneakInput >= 1 /* Debouncing */ && this.ticksSinceLastSneakInput < 6) {
                settings.sneakToggled = !settings.sneakToggled;
            }
            this.ticksSinceLastSneakInput = 0;
        } else {
            if (++this.ticksSinceLastSneakInput > 7) {
                this.ticksSinceLastSneakInput = 7;
            }
        }
    }
}
