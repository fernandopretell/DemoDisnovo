package com.limapps.base.models.rc

data class RappiCreditsState(
        val isActive: Boolean,
        val availableRappiCreditsTotal: Double,
        val totalRappiCredits: Double = -1.0
)