package com.starshootercity.originsmobs

import com.starshootercity.originsmobs.abilities.*
import com.starshootercity.OriginsAddon
import com.starshootercity.abilities.Ability
import org.bukkit.Bukkit

class OriginsMobs : OriginsAddon() {

    override fun getNamespace(): String = "moborigins"

    override fun getAbilities(): List<Ability> = listOf(
        SmallBug(),
        SmallFox(),
        LowerTotemChance(),
        SnowTrail(),
        StrongerSnowballs(),
        BeeWings(),
        Stinger(),
        BecomesElderGuardian(),
        WarpedFungusEater(),
        WaterCombatant(),
        QueenBee(),
        Undead(),
        Sly(),
        TimidCreature(),
        PillagerAligned(),
        Illager(),
        WitchParticles(),
        MiningFatigueImmune(),
        SmallWeak(),
        SmallWeakKnockback(),
        RideableCreature(),
        GuardianAlly(),
        SurfaceSlowness(),
        SurfaceWeakness(),
        GuardianSpikes(),
        PrismarineSkin(),
        CarefulGatherer(),
        FrigidStrength(),
        BetterBerries(),
        WolfBody(),
        AlphaWolf(),
        ItemCollector(),
        BetterPotions(),
        ElderMagic(),
        ElderSpikes(),
        WaterVision(),
        SummonFangs(),
        FullMoon(),
        FullMoonHealth(),
        FullMoonAttack(),
        WolfPack(),
        WolfPackAttack(),
        ZombieHunger(),
        Temperature.INSTANCE,
        Overheat(),
        Melting(),
        MeltingSpeed(),
        WolfHowl(),
        TridentExpert(),
        FlowerPower(),
        Bouncy(),
        LavaWalk(),
        WaterBreathing(),
        Split(),
        PotionAction()
    )

    init {
        instance = this
    }

    companion object {
        private lateinit var nmsInvoker: MobsNMSInvoker

        private lateinit var instance: OriginsMobs

        private fun initializeNMSInvoker() {
            nmsInvoker = when (Bukkit.getMinecraftVersion()) {
                "1.19" -> MobsNMSInvokerV1_19()
                "1.19.1" -> MobsNMSInvokerV1_19_1()
                "1.19.2" -> MobsNMSInvokerV1_19_2()
                "1.19.3" -> MobsNMSInvokerV1_19_3()
                "1.19.4" -> MobsNMSInvokerV1_19_4()
                "1.20" -> MobsNMSInvokerV1_20()
                "1.20.1" -> MobsNMSInvokerV1_20_1()
                "1.20.2" -> MobsNMSInvokerV1_20_2()
                "1.20.3" -> MobsNMSInvokerV1_20_3()
                "1.20.4" -> MobsNMSInvokerV1_20_4()
                "1.20.5", "1.20.6" -> MobsNMSInvokerV1_20_6()
                "1.21" -> MobsNMSInvokerV1_21()
                "1.21.1" -> MobsNMSInvokerV1_21_1()
                "1.21.2", "1.21.3" -> MobsNMSInvokerV1_21_3()
                "1.21.4" -> MobsNMSInvokerV1_21_4()
                else -> throw IllegalStateException("Unexpected version: ${Bukkit.getMinecraftVersion()} only versions 1.20 - 1.20.6 are supported")
            }
        }

        @JvmStatic
        fun getNMSInvoker(): MobsNMSInvoker = nmsInvoker

        @JvmStatic
        fun getInstance(): OriginsMobs = instance
    }

    override fun onRegister() {
        initializeNMSInvoker()
    }
}
