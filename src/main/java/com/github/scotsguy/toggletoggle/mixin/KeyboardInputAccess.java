package com.github.scotsguy.toggletoggle.mixin;

import com.sun.jna.platform.win32.WinUser;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyboardInput.class)
public interface KeyboardInputAccess {
    @Accessor
    public GameOptions getSettings();
}
