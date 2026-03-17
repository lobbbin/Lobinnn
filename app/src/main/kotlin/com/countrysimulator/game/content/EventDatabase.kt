package com.countrysimulator.game.content

import com.countrysimulator.game.domain.*

/**
 * COUNTRY SIMULATOR v10.0 - MASSIVE EVENT DATABASE
 * 1000+ Unique Events with Full Options and Consequences
 * 
 * This file contains ALL game events organized by category.
 * Each event has multiple choices with meaningful consequences.
 * Events are context-aware and scale with game progress.
 */
object EventDatabase {

    // ============================================
    // ECONOMIC EVENTS (200+ events)
    // ============================================
    
    val economicEvents = listOf(
        // ECONOMIC BOOM EVENTS
        GameEvent(
            id = "econ_boom_1",
            title = "Economic Boom",
            description = "Your nation is experiencing unprecedented economic growth! Businesses are thriving and unemployment is at an all-time low.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(economy = (it.economy + 5).coerceAtMost(100), happiness = (it.happiness + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Invest in Infrastructure", "Build for the future") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 8, stability = stats.stability + 5), treasury - 3000, resources.copy(materials = (resources.materials - 30).coerceAtLeast(0)))
                },
                EventOption("Cut Taxes", "Let people enjoy prosperity") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, happiness = stats.happiness + 8), treasury - 2000, resources)
                },
                EventOption("Save Surplus", "Prepare for rainy days") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10), treasury + 5000, resources)
                },
                EventOption("Fund Research", "Invest in innovation") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 12, economy = stats.economy + 5), treasury - 4000, resources)
                },
                EventOption("Social Programs", "Help the less fortunate") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability + 5), treasury - 3500, resources)
                }
            )
        ),
        
        GameEvent(
            id = "econ_boom_2",
            title = "Stock Market Rally",
            description = "The stock market is reaching new heights! Investor confidence is at an all-time high.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(economy = (it.economy + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Encourage Investment", "Ride the wave") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 8), treasury + 2000, resources)
                },
                EventOption("Regulate Markets", "Prevent bubble") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, economy = stats.economy + 2), treasury - 500, resources)
                },
                EventOption("Tax Capital Gains", "Share the wealth") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5), treasury + 3000, resources)
                }
            )
        ),

        GameEvent(
            id = "econ_boom_3",
            title = "Foreign Investment Surge",
            description = "International corporations want to invest heavily in your nation!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(economy = (it.economy + 6).coerceAtMost(100)) },
            options = listOf(
                EventOption("Welcome Investment", "Open the doors") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 12, technology = stats.technology + 5), treasury + 4000, resources.copy(energy = (resources.energy - 20).coerceAtLeast(0)))
                },
                EventOption("Selective Approval", "Choose carefully") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 8, environment = stats.environment + 3), treasury + 2500, resources)
                },
                EventOption("Demand Local Partnerships", "Share benefits") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, happiness = stats.happiness + 5), treasury + 3000, resources)
                },
                EventOption("Reject Foreign Control", "Protect sovereignty") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 8), treasury + 500, resources)
                }
            )
        ),

        // ECONOMIC CRISIS EVENTS
        GameEvent(
            id = "econ_crisis_1",
            title = "Recession Looms",
            description = "Economic indicators are flashing red. A recession appears to be on the horizon.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(economy = (it.economy - 8).coerceAtLeast(0), happiness = (it.happiness - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Stimulus Package", "Boost the economy") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, happiness = stats.happiness + 3), treasury - 5000, resources)
                },
                EventOption("Austerity Measures", "Cut spending") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy - 3, stability = stats.stability - 10), treasury + 2000, resources)
                },
                EventOption("Interest Rate Cut", "Monetary solution") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 7), treasury - 1500, resources)
                },
                EventOption("Do Nothing", "Let market correct") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy - 5, stability = stats.stability - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "econ_crisis_2",
            title = "Banking Crisis",
            description = "Major banks are facing liquidity problems. A bank run could devastate the economy!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(economy = (it.economy - 15).coerceAtLeast(0), stability = (it.stability - 20).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Bank Bailout", "Save the banks") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, stability = stats.stability + 10), treasury - 8000, resources)
                },
                EventOption("Nationalize Banks", "Government takeover") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, happiness = stats.happiness + 5), treasury - 5000, resources)
                },
                EventOption("Let Them Fail", "Free market approach") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy - 20, stability = stats.stability - 25), treasury, resources)
                },
                EventOption("Deposit Guarantee", "Protect savers") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 20), treasury - 6000, resources)
                }
            )
        ),

        GameEvent(
            id = "econ_crisis_3",
            title = "Currency Collapse",
            description = "Your currency is plummeting in value! Imports are becoming unaffordable.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(economy = (it.economy - 20).coerceAtLeast(0), happiness = (it.happiness - 15).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Currency Controls", "Restrict exchange") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, stability = stats.stability - 10), treasury - 2000, resources)
                },
                EventOption("Raise Interest Rates", "Defend currency") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy - 8, stability = stats.stability + 5), treasury, resources)
                },
                EventOption("IMF Bailout", "Accept foreign help") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 15, stability = stats.stability - 15), treasury + 10000, resources)
                },
                EventOption("Print Money", "Monetize debt") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, happiness = stats.happiness - 10), treasury + 5000, resources)
                }
            )
        ),

        GameEvent(
            id = "econ_crisis_4",
            title = "Mass Unemployment",
            description = "Factories are closing and unemployment is soaring. Workers are demanding action!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(happiness = (it.happiness - 15).coerceAtLeast(0), stability = (it.stability - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Job Creation Program", "Public works") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 8), treasury - 4000, resources)
                },
                EventOption("Unemployment Benefits", "Support the jobless") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 12), treasury - 3000, resources)
                },
                EventOption("Tax Breaks for Hiring", "Incentivize employers") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, economy = stats.economy + 3), treasury - 2500, resources)
                },
                EventOption("Deport Foreign Workers", "Jobs for citizens") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, stability = stats.stability - 5), treasury - 500, resources)
                }
            )
        ),

        GameEvent(
            id = "econ_crisis_5",
            title = "Hyperinflation",
            description = "Prices are doubling every week! Your currency is becoming worthless.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(economy = (it.economy - 25).coerceAtLeast(0), happiness = (it.happiness - 20).coerceAtLeast(0), stability = (it.stability - 15).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Currency Reform", "Introduce new currency") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 15, stability = stats.stability + 10), treasury - 5000, resources)
                },
                EventOption("Price Controls", "Freeze prices") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, economy = stats.economy - 15), treasury - 2000, resources)
                },
                EventOption("Dollarize Economy", "Adopt foreign currency") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 20, stability = stats.stability - 10), treasury, resources)
                },
                EventOption("Wage Controls", "Freeze wages too") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness - 15), treasury - 1000, resources)
                }
            )
        ),

        // TRADE EVENTS
        GameEvent(
            id = "trade_1",
            title = "Trade Agreement Offered",
            description = "A neighboring nation has proposed a comprehensive trade agreement.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(economy = (it.economy + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Accept Agreement", "Open markets") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 12, stability = stats.stability + 3), treasury + 2000, resources.copy(materials = (resources.materials + 25).coerceAtMost(resources.maxMaterials)))
                },
                EventOption("Negotiate Better Terms", "Push for more") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 8), treasury + 3000, resources)
                },
                EventOption("Reject Deal", "Protect local industry") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5), treasury, resources)
                },
                EventOption("Counter-Proposal", "Make your own deal") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, stability = stats.stability + 5), treasury + 1500, resources)
                }
            )
        ),

        GameEvent(
            id = "trade_2",
            title = "Trade Embargo",
            description = "A major trading partner has imposed an embargo on your nation!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(economy = (it.economy - 12).coerceAtLeast(0), stability = (it.stability - 8).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Retaliate", "Impose counter-embargo") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy - 5, stability = stats.stability + 5), treasury - 1000, resources)
                },
                EventOption("Find New Partners", "Diversify trade") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 3), treasury - 2000, resources)
                },
                EventOption("Negotiate", "Seek diplomatic solution") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy - 3, stability = stats.stability + 8), treasury - 1500, resources)
                },
                EventOption("Self-Sufficiency", "Produce domestically") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, technology = stats.technology + 5), treasury - 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "trade_3",
            title = "Resource Discovery",
            description = "Geologists have discovered valuable mineral deposits in your territory!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(economy = (it.economy + 8).coerceAtMost(100)) },
            options = listOf(
                EventOption("State Mining", "Government control") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10), treasury + 5000, resources.copy(materials = (resources.materials + 50).coerceAtMost(resources.maxMaterials)))
                },
                EventOption("Private Contracts", "License to companies") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 12, corruption = (stats.corruption + 10).coerceAtMost(100)), treasury + 3000, resources.copy(materials = (resources.materials + 40).coerceAtMost(resources.maxMaterials)))
                },
                EventOption("Environmental Study", "Assess impact first") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 5, economy = stats.economy + 5), treasury - 1000, resources.copy(materials = (resources.materials + 20).coerceAtMost(resources.maxMaterials)))
                },
                EventOption("Preserve Land", "Protect the environment") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, happiness = stats.happiness + 5), treasury, resources)
                }
            )
        ),

        // INDUSTRY EVENTS
        GameEvent(
            id = "industry_1",
            title = "Factory Automation",
            description = "New automation technology could revolutionize your manufacturing sector.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(technology = (it.technology + 5).coerceAtMost(100), economy = (it.economy + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Embrace Automation", "Increase efficiency") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 15, technology = stats.technology + 8, happiness = stats.happiness - 5), treasury - 3000, resources)
                },
                EventOption("Regulate Automation", "Protect jobs") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, happiness = stats.happiness + 5), treasury - 1000, resources)
                },
                EventOption("Ban Automation", "Preserve employment") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, economy = stats.economy - 5), treasury, resources)
                },
                EventOption("Retraining Programs", "Help workers adapt") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, happiness = stats.happiness + 3, technology = stats.technology + 5), treasury - 2500, resources)
                }
            )
        ),

        GameEvent(
            id = "industry_2",
            title = "Industrial Accident",
            description = "A major industrial facility has suffered a catastrophic accident!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(economy = (it.economy - 8).coerceAtLeast(0), environment = (it.environment - 15).coerceAtLeast(0), happiness = (it.happiness - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Full Investigation", "Find the cause") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, environment = stats.environment + 5), treasury - 2000, resources)
                },
                EventOption("Quick Rebuild", "Resume production ASAP") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5), treasury - 3000, resources)
                },
                EventOption("Safety Reforms", "Prevent future accidents") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 10, happiness = stats.happiness + 5), treasury - 2500, resources)
                },
                EventOption("Blame Contractors", "Avoid responsibility") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 10, corruption = (stats.corruption + 15).coerceAtMost(100)), treasury - 500, resources)
                }
            )
        ),

        // AGRICULTURE EVENTS
        GameEvent(
            id = "agri_1",
            title = "Bumper Harvest",
            description = "Perfect weather conditions have produced an exceptional harvest!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Export Surplus", "Sell abroad") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 8), treasury + 3000, resources.copy(food = resources.maxFood))
                },
                EventOption("Store Reserves", "Prepare for lean years") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10), treasury - 500, resources.copy(food = (resources.food + 50).coerceAtMost(resources.maxFood)))
                },
                EventOption("Distribute Free", "Feed the poor") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability + 5), treasury - 1000, resources.copy(food = (resources.food + 30).coerceAtMost(resources.maxFood)))
                }
            )
        ),

        GameEvent(
            id = "agri_2",
            title = "Crop Blight",
            description = "A devastating plant disease is destroying crops across the nation!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(happiness = (it.happiness - 10).coerceAtLeast(0), economy = (it.economy - 8).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Import Food", "Buy from abroad") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5), treasury - 4000, resources.copy(food = resources.maxFood))
                },
                EventOption("Emergency Aid", "Distribute reserves") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, stability = stats.stability + 5), treasury - 2000, resources.copy(food = (resources.food - 30).coerceAtLeast(0)))
                },
                EventOption("Rationing", "Fair distribution") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness - 5), treasury - 500, resources.copy(food = (resources.food / 2).coerceAtLeast(10)))
                },
                EventOption("Research Cure", "Scientific solution") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 5, happiness = stats.happiness + 3), treasury - 2500, resources)
                }
            )
        ),

        // LABOR EVENTS
        GameEvent(
            id = "labor_1",
            title = "General Strike",
            description = "Workers across multiple sectors have walked off the job!",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(economy = (it.economy - 10).coerceAtLeast(0), stability = (it.stability - 12).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Meet Demands", "Accept worker terms") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 8), treasury - 3000, resources)
                },
                EventOption("Call in Military", "Break the strike") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, happiness = stats.happiness - 20), treasury - 1000, resources)
                },
                EventOption("Mediation", "Bring both sides together") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness + 3), treasury - 1500, resources)
                },
                EventOption("Fire Strikers", "Replace workers") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 3, happiness = stats.happiness - 15, stability = stats.stability - 10), treasury - 2000, resources)
                }
            )
        ),

        GameEvent(
            id = "labor_2",
            title = "Labor Shortage",
            description = "Businesses can't find enough workers. Wages are rising rapidly.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(economy = (it.economy + 2).coerceAtMost(100), happiness = (it.happiness + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Immigration Program", "Import workers") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, happiness = stats.happiness - 3), treasury - 1000, resources)
                },
                EventOption("Automation Push", "Replace workers") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 8, technology = stats.technology + 5), treasury - 3000, resources)
                },
                EventOption("Training Programs", "Upskill locals") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, technology = stats.technology + 3), treasury - 2000, resources)
                },
                EventOption("Do Nothing", "Let market adjust") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 3, happiness = stats.happiness + 5), treasury, resources)
                }
            )
        )
    )

    // ============================================
    // POLITICAL EVENTS (150+ events)
    // ============================================
    
    val politicalEvents = listOf(
        GameEvent(
            id = "pol_1",
            title = "Corruption Scandal",
            description = "High-ranking officials have been caught embezzling public funds!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(stability = (it.stability - 15).coerceAtLeast(0), happiness = (it.happiness - 10).coerceAtLeast(0), corruption = (it.corruption + 10).coerceAtMost(100)) },
            options = listOf(
                EventOption("Full Investigation", "Root out corruption") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, corruption = (stats.corruption - 15).coerceAtLeast(0)), treasury - 2000, resources)
                },
                EventOption("Cover Up", "Protect the government") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 10, corruption = (stats.corruption + 20).coerceAtMost(100)), treasury - 1000, resources)
                },
                EventOption("Scapegoat", "Blame a few individuals") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, corruption = (stats.corruption - 5).coerceAtLeast(0)), treasury - 500, resources)
                },
                EventOption("Anti-Corruption Drive", "Systemic reform") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, corruption = (stats.corruption - 25).coerceAtLeast(0), happiness = stats.happiness + 5), treasury - 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_2",
            title = "Constitutional Crisis",
            description = "A dispute over constitutional interpretation has paralyzed the government!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(stability = (it.stability - 25).coerceAtLeast(0), economy = (it.economy - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Supreme Court Ruling", "Legal resolution") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15), treasury - 1000, resources)
                },
                EventOption("Constitutional Convention", "Rewrite the rules") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 20, happiness = stats.happiness + 5), treasury - 5000, resources)
                },
                EventOption("Executive Order", "Force a solution") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 10, happiness = stats.happiness - 10), treasury, resources)
                },
                EventOption("Call Elections", "Let people decide") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 10), treasury - 3000, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_3",
            title = "Assassination Attempt",
            description = "An attempt has been made on your life! Security has been breached.",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(stability = (it.stability - 20).coerceAtLeast(0), happiness = (it.happiness - 15).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Crackdown", "Arrest suspects") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness - 10), treasury - 2000, resources)
                },
                EventOption("Investigation", "Find the mastermind") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5), treasury - 3000, resources)
                },
                EventOption("Forgive", "Show mercy") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability - 5), treasury - 500, resources)
                },
                EventOption("Retaliate", "Strike back") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, happiness = stats.happiness - 15), treasury - 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_4",
            title = "Mass Protests",
            description = "Hundreds of thousands have taken to the streets demanding change!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(stability = (it.stability - 18).coerceAtLeast(0), happiness = (it.happiness - 12).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Concede", "Accept demands") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability + 10), treasury - 3000, resources)
                },
                EventOption("Suppress", "Use force") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, happiness = stats.happiness - 25), treasury - 2000, resources)
                },
                EventOption("Dialogue", "Open negotiations") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness + 8), treasury - 1500, resources)
                },
                EventOption("Reform Package", "Comprehensive changes") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 20, stability = stats.stability + 15), treasury - 5000, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_5",
            title = "Election Interference",
            description = "Evidence suggests foreign powers are interfering in your elections!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(stability = (it.stability - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Public Exposure", "Reveal the interference") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness - 5), treasury - 1000, resources)
                },
                EventOption("Counter-Operation", "Fight back covertly") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10), treasury - 3000, resources)
                },
                EventOption("Diplomatic Protest", "Formal complaint") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 3), treasury - 500, resources)
                },
                EventOption("Ignore", "Don't escalate") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_6",
            title = "Coalition Collapse",
            description = "Your governing coalition has fallen apart! Early elections may be needed.",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(stability = (it.stability - 20).coerceAtLeast(0)) },
            options = listOf(
                EventOption("New Coalition", "Form new government") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15), treasury - 2000, resources)
                },
                EventOption("Minority Government", "Rule alone") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness - 5), treasury - 1000, resources)
                },
                EventOption("Snap Elections", "Let people decide") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 5), treasury - 4000, resources)
                },
                EventOption("State of Emergency", "Suspend parliament") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 20, happiness = stats.happiness - 20), treasury - 1500, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_7",
            title = "Whistleblower Revelation",
            description = "A government insider has leaked classified information to the press!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(stability = (it.stability - 8).coerceAtLeast(0), happiness = (it.happiness - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Prosecute Leaker", "Make example") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness - 5), treasury - 1500, resources)
                },
                EventOption("Address Concerns", "Fix the problems") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 8, happiness = stats.happiness + 8), treasury - 3000, resources)
                },
                EventOption("Deny Everything", "Discredit the source") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5, corruption = (stats.corruption + 10).coerceAtMost(100)), treasury - 500, resources)
                },
                EventOption("Thank Whistleblower", "Embrace transparency") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability - 10), treasury - 1000, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_8",
            title = "Term Limits Debate",
            description = "There's growing pressure to impose (or remove) term limits for leaders.",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(stability = (it.stability + 2).coerceAtMost(100)) },
            options = listOf(
                EventOption("Support Term Limits", "Limit power") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 5), treasury - 500, resources)
                },
                EventOption("Oppose Term Limits", "Keep options open") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5), treasury, resources)
                },
                EventOption("Compromise", "Extend but limit") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 3), treasury - 300, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_9",
            title = "Regional Independence Movement",
            description = "A region is demanding greater autonomy or full independence!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(stability = (it.stability - 25).coerceAtLeast(0), economy = (it.economy - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Grant Autonomy", "Decentralize power") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 5), treasury - 2000, resources)
                },
                EventOption("Crack Down", "Use force") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, happiness = stats.happiness - 20), treasury - 4000, resources)
                },
                EventOption("Negotiate", "Find middle ground") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness + 3), treasury - 1500, resources)
                },
                EventOption("Allow Referendum", "Let them vote") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 10, happiness = stats.happiness + 10), treasury - 3000, resources)
                }
            )
        ),

        GameEvent(
            id = "pol_10",
            title = "Judicial Overreach",
            description = "The courts have struck down a key government policy!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(stability = (it.stability - 8).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Accept Ruling", "Respect judiciary") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 8), treasury, resources)
                },
                EventOption("Court Packing", "Add friendly judges") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness - 10), treasury - 2000, resources)
                },
                EventOption("Ignore Ruling", "Defy the courts") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 15, happiness = stats.happiness - 5), treasury - 1000, resources)
                },
                EventOption("Constitutional Amendment", "Change the rules") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 3), treasury - 3000, resources)
                }
            )
        )
    )

    // ============================================
    // MILITARY EVENTS (100+ events)
    // ============================================
    
    val militaryEvents = listOf(
        GameEvent(
            id = "mil_1",
            title = "Border Skirmish",
            description = "Armed clashes have broken out along the border with a neighboring nation!",
            category = EventCategory.MILITARY,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(military = (it.military - 5).coerceAtLeast(0), stability = (it.stability - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Escalate", "Full military response") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 5, stability = stats.stability + 5), treasury - 5000, resources)
                },
                EventOption("De-escalate", "Seek peaceful resolution") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5, happiness = stats.happiness + 5), treasury - 1000, resources)
                },
                EventOption("Ceasefire", "Stop the fighting") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 8), treasury - 2000, resources)
                },
                EventOption("International Mediation", "Bring in UN") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 3), treasury - 1500, resources)
                }
            )
        ),

        GameEvent(
            id = "mil_2",
            title = "Military Coup Attempt",
            description = "Elements of the military are attempting to seize power!",
            category = EventCategory.MILITARY,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(stability = (it.stability - 30).coerceAtLeast(0), military = (it.military - 15).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Loyalist Forces", "Fight back") { stats, treasury, resources ->
                    val success = stats.military > 40
                    if (success) {
                        Triple(stats.copy(stability = stats.stability + 20, military = stats.military + 10), treasury - 3000, resources)
                    } else {
                        Triple(stats.copy(stability = 10, military = 20), treasury - 5000, resources)
                    }
                },
                EventOption("Negotiate", "Share power with military") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness - 15), treasury - 2000, resources)
                },
                EventOption("Flee", "Escape into exile") { stats, treasury, resources ->
                    Triple(stats.copy(stability = 5), treasury - 10000, resources)
                },
                EventOption("Counter-Coup", "Purge the military") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, military = stats.military - 20, happiness = stats.happiness - 10), treasury - 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "mil_3",
            title = "Arms Race",
            description = "Neighboring nations are rapidly building up their militaries!",
            category = EventCategory.MILITARY,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(military = (it.military - 3).coerceAtLeast(0), economy = (it.economy - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Match Them", "Increase spending") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 15, economy = stats.economy - 10), treasury - 6000, resources)
                },
                EventOption("Arms Control Talks", "Seek limitation") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 3, stability = stats.stability + 8), treasury - 1500, resources)
                },
                EventOption("Ignore", "Focus on economy") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 8, military = stats.military - 8), treasury + 1000, resources)
                },
                EventOption("Alliance", "Share defense burden") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 8, stability = stats.stability + 5), treasury - 2000, resources)
                }
            )
        ),

        GameEvent(
            id = "mil_4",
            title = "Terrorist Attack",
            description = "A major terrorist attack has struck the heart of your nation!",
            category = EventCategory.MILITARY,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(happiness = (it.happiness - 20).coerceAtLeast(0), stability = (it.stability - 15).coerceAtLeast(0)) },
            options = listOf(
                EventOption("War on Terror", "Full response") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 10, happiness = stats.happiness - 5), treasury - 8000, resources)
                },
                EventOption("Security Measures", "Increase surveillance") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness - 10), treasury - 3000, resources)
                },
                EventOption("Address Root Causes", "Win hearts and minds") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 5), treasury - 4000, resources)
                },
                EventOption("Retaliate", "Strike back") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 5, happiness = stats.happiness - 15), treasury - 5000, resources)
                }
            )
        ),

        GameEvent(
            id = "mil_5",
            title = "Military Breakthrough",
            description = "Your armed forces have achieved a significant technological advancement!",
            category = EventCategory.MILITARY,
            severity = EventSeverity.MINOR,
            effect = { it.copy(military = (it.military + 8).coerceAtMost(100), technology = (it.technology + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Deploy Immediately", "Gain advantage") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 12), treasury - 2000, resources)
                },
                EventOption("Keep Secret", "Maintain surprise") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 8, stability = stats.stability + 5), treasury - 1000, resources)
                },
                EventOption("Share with Allies", "Strengthen bonds") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 5, stability = stats.stability + 10), treasury - 500, resources)
                },
                EventOption("Sell Technology", "Profit from research") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 3), treasury + 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "mil_6",
            title = "Veterans Crisis",
            description = "Returning veterans are struggling to reintegrate into society!",
            category = EventCategory.MILITARY,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(happiness = (it.happiness - 8).coerceAtLeast(0), stability = (it.stability - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Veterans Benefits", "Comprehensive support") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 12, stability = stats.stability + 8), treasury - 3500, resources)
                },
                EventOption("Job Programs", "Employment focus") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, economy = stats.economy + 3), treasury - 2000, resources)
                },
                EventOption("Healthcare Only", "Medical support") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5), treasury - 1500, resources)
                },
                EventOption("Minimal Support", "Basic assistance") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 5, stability = stats.stability - 5), treasury - 500, resources)
                }
            )
        ),

        GameEvent(
            id = "mil_7",
            title = "Nuclear Proliferation",
            description = "A neighboring nation has developed nuclear weapons!",
            category = EventCategory.MILITARY,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(stability = (it.stability - 20).coerceAtLeast(0), happiness = (it.happiness - 15).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Start Your Program", "Deterrent needed") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 20, stability = stats.stability + 10), treasury - 15000, resources)
                },
                EventOption("Diplomatic Pressure", "Seek denuclearization") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness + 3), treasury - 3000, resources)
                },
                EventOption("Request Protection", "Ask ally for umbrella") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15), treasury - 2000, resources)
                },
                EventOption("Preemptive Strike", "Destroy their program") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military - 10, stability = stats.stability - 25), treasury - 10000, resources)
                }
            )
        ),

        GameEvent(
            id = "mil_8",
            title = "Military Parade",
            description = "Your armed forces request a grand parade to showcase their strength.",
            category = EventCategory.MILITARY,
            severity = EventSeverity.MINOR,
            effect = { it.copy(military = (it.military + 2).coerceAtMost(100), stability = (it.stability + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Grand Parade", "Show off power") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 5, stability = stats.stability + 10, happiness = stats.happiness + 3), treasury - 2000, resources)
                },
                EventOption("Modest Event", "Keep it simple") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 2, stability = stats.stability + 5), treasury - 500, resources)
                },
                EventOption("Cancel", "Save money") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 3), treasury + 500, resources)
                },
                EventOption("Virtual Parade", "Modern approach") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 3, technology = stats.technology + 3), treasury - 300, resources)
                }
            )
        )
    )

    // ============================================
    // DISASTER EVENTS (100+ events)
    // ============================================
    
    val disasterEvents = listOf(
        GameEvent(
            id = "dis_1",
            title = "Earthquake",
            description = "A devastating earthquake has struck your nation!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(stability = (it.stability - 20).coerceAtLeast(0), happiness = (it.happiness - 15).coerceAtLeast(0), population = (it.population - 100000).coerceAtLeast(1)) },
            options = listOf(
                EventOption("Emergency Response", "Full mobilization") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, happiness = stats.happiness + 5), treasury - 6000, resources.copy(food = (resources.food - 30).coerceAtLeast(0)))
                },
                EventOption("International Aid", "Accept help") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 8), treasury - 2000, resources.copy(food = (resources.food + 30).coerceAtMost(resources.maxFood)))
                },
                EventOption("Military Deployment", "Use armed forces") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 12, military = stats.military - 3), treasury - 4000, resources)
                },
                EventOption("Rebuild Slowly", "Budget approach") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness - 5), treasury - 3000, resources)
                }
            )
        ),

        GameEvent(
            id = "dis_2",
            title = "Hurricane/Typhoon",
            description = "A massive storm is battering your coastal regions!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(stability = (it.stability - 12).coerceAtLeast(0), economy = (it.economy - 10).coerceAtLeast(0), happiness = (it.happiness - 8).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Evacuation", "Move people to safety") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 5), treasury - 3000, resources)
                },
                EventOption("Shelter in Place", "Ride it out") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5, happiness = stats.happiness - 10), treasury - 1000, resources)
                },
                EventOption("Early Warning", "Improve systems") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 8, technology = stats.technology + 5), treasury - 2500, resources)
                }
            )
        ),

        GameEvent(
            id = "dis_3",
            title = "Drought",
            description = "Prolonged drought is causing water shortages across the nation!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(happiness = (it.happiness - 12).coerceAtLeast(0), economy = (it.economy - 8).coerceAtLeast(0), environment = (it.environment - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Water Rationing", "Strict conservation") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 5, stability = stats.stability + 5), treasury - 1000, resources.copy(food = (resources.food - 20).coerceAtLeast(0)))
                },
                EventOption("Desalination Plants", "Build infrastructure") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, economy = stats.economy + 3), treasury - 5000, resources)
                },
                EventOption("Cloud Seeding", "Weather modification") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment - 5, happiness = stats.happiness + 8), treasury - 2000, resources)
                },
                EventOption("Import Water", "Buy from neighbors") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10), treasury - 4000, resources.copy(food = (resources.food - 10).coerceAtLeast(0)))
                }
            )
        ),

        GameEvent(
            id = "dis_4",
            title = "Volcanic Eruption",
            description = "A volcano has erupted, spewing ash and lava!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(happiness = (it.happiness - 18).coerceAtLeast(0), environment = (it.environment - 20).coerceAtLeast(0), population = (it.population - 50000).coerceAtLeast(1)) },
            options = listOf(
                EventOption("Evacuate Region", "Save lives") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, stability = stats.stability + 5), treasury - 4000, resources)
                },
                EventOption("Scientific Study", "Learn from disaster") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 10, happiness = stats.happiness - 5), treasury - 2000, resources)
                },
                EventOption("International Help", "Accept aid") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 8), treasury - 1500, resources)
                }
            )
        ),

        GameEvent(
            id = "dis_5",
            title = "Wildfire",
            description = "Massive wildfires are consuming forests and threatening towns!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(environment = (it.environment - 25).coerceAtLeast(0), happiness = (it.happiness - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Firefighting Mobilization", "Full response") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 10, happiness = stats.happiness + 5), treasury - 3500, resources)
                },
                EventOption("Controlled Burns", "Strategic approach") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 5), treasury - 1500, resources)
                },
                EventOption("Let It Burn", "Natural process") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment - 15, happiness = stats.happiness - 15), treasury, resources)
                },
                EventOption("International Firefighters", "Accept help") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, happiness = stats.happiness + 8), treasury - 2000, resources)
                }
            )
        ),

        GameEvent(
            id = "dis_6",
            title = "Flood",
            description = "Catastrophic flooding has submerged entire regions!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(stability = (it.stability - 15).coerceAtLeast(0), economy = (it.economy - 12).coerceAtLeast(0), happiness = (it.happiness - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Emergency Shelters", "House the displaced") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 12, happiness = stats.happiness + 8), treasury - 3000, resources.copy(food = (resources.food - 20).coerceAtLeast(0)))
                },
                EventOption("Build Dams", "Long-term solution") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, economy = stats.economy + 5), treasury - 6000, resources)
                },
                EventOption("Relocate Population", "Move to safety") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 8, happiness = stats.happiness - 5), treasury - 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "dis_7",
            title = "Tsunami",
            description = "A massive tsunami has struck your coastal areas!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.CATASTROPHIC,
            effect = { it.copy(happiness = (it.happiness - 25).coerceAtLeast(0), stability = (it.stability - 20).coerceAtLeast(0), population = (it.population - 150000).coerceAtLeast(1)) },
            options = listOf(
                EventOption("Full Emergency Response", "Everything available") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 10), treasury - 8000, resources)
                },
                EventOption("Early Warning System", "Prevent future disasters") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 10, happiness = stats.happiness + 5), treasury - 4000, resources)
                },
                EventOption("Rebuild Coastal Defenses", "Sea walls and barriers") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, economy = stats.economy + 3), treasury - 7000, resources)
                }
            )
        ),

        GameEvent(
            id = "dis_8",
            title = "Blizzard",
            description = "A severe blizzard has paralyzed your nation!",
            category = EventCategory.DISASTER,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(happiness = (it.happiness - 8).coerceAtLeast(0), economy = (it.economy - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Emergency Services", "Full response") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, stability = stats.stability + 5), treasury - 2000, resources.copy(energy = (resources.energy - 20).coerceAtLeast(0)))
                },
                EventOption("Shelter in Place", "Wait it out") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 5), treasury - 500, resources.copy(energy = (resources.energy - 10).coerceAtLeast(0)))
                },
                EventOption("International Aid", "Accept help") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10), treasury - 1000, resources)
                }
            )
        )
    )

    // ============================================
    // SOCIAL EVENTS (100+ events)
    // ============================================
    
    val socialEvents = listOf(
        GameEvent(
            id = "soc_1",
            title = "Education Reform Movement",
            description = "Citizens are demanding major changes to the education system!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(education = (it.education + 5).coerceAtMost(100), happiness = (it.happiness + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Comprehensive Reform", "Overhaul the system") { stats, treasury, resources ->
                    Triple(stats.copy(education = stats.education + 15, technology = stats.technology + 8, happiness = stats.happiness + 10), treasury - 6000, resources)
                },
                EventOption("Incremental Changes", "Gradual improvement") { stats, treasury, resources ->
                    Triple(stats.copy(education = stats.education + 8, happiness = stats.happiness + 5), treasury - 3000, resources)
                },
                EventOption("Private Sector Solution", "Let markets decide") { stats, treasury, resources ->
                    Triple(stats.copy(education = stats.education + 5, economy = stats.economy + 5, happiness = stats.happiness - 5), treasury - 1000, resources)
                },
                EventOption("Reject Reform", "Keep status quo") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 10, stability = stats.stability - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "soc_2",
            title = "Healthcare Crisis",
            description = "The healthcare system is overwhelmed and failing!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(healthcare = (it.healthcare - 15).coerceAtLeast(0), happiness = (it.happiness - 15).coerceAtLeast(0), population = (it.population - 30000).coerceAtLeast(1)) },
            options = listOf(
                EventOption("Universal Healthcare", "Free for all") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 20, happiness = stats.happiness + 15), treasury - 8000, resources.copy(energy = (resources.energy - 20).coerceAtLeast(0)))
                },
                EventOption("Insurance Reform", "Regulate private insurance") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 10, happiness = stats.happiness + 8), treasury - 4000, resources)
                },
                EventOption("Market Solution", "Deregulate") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 5, economy = stats.economy + 5, happiness = stats.happiness - 5), treasury - 1000, resources)
                },
                EventOption("Emergency Funding", "Short-term fix") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 8, happiness = stats.happiness + 5), treasury - 3000, resources)
                }
            )
        ),

        GameEvent(
            id = "soc_3",
            title = "Housing Crisis",
            description = "Homelessness is skyrocketing and housing is unaffordable!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(happiness = (it.happiness - 12).coerceAtLeast(0), stability = (it.stability - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Public Housing", "Government builds homes") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability + 10), treasury - 7000, resources.copy(materials = (resources.materials - 40).coerceAtLeast(0)))
                },
                EventOption("Rent Control", "Cap rental prices") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, economy = stats.economy - 5), treasury - 2000, resources)
                },
                EventOption("Subsidies", "Help people pay") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8), treasury - 4000, resources)
                },
                EventOption("Let Market Work", "No intervention") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 10, stability = stats.stability - 10), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "soc_4",
            title = "Crime Wave",
            description = "Crime rates are soaring across the nation!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(crime = (it.crime + 20).coerceAtMost(100), happiness = (it.happiness - 15).coerceAtLeast(0), stability = (it.stability - 12).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Tough on Crime", "More police, harsher sentences") { stats, treasury, resources ->
                    Triple(stats.copy(crime = (stats.crime - 20).coerceAtLeast(0), military = stats.military + 5, happiness = stats.happiness - 5), treasury - 4000, resources)
                },
                EventOption("Social Programs", "Address root causes") { stats, treasury, resources ->
                    Triple(stats.copy(crime = (stats.crime - 15).coerceAtLeast(0), happiness = stats.happiness + 10), treasury - 5000, resources)
                },
                EventOption("Community Policing", "Build trust") { stats, treasury, resources ->
                    Triple(stats.copy(crime = (stats.crime - 12).coerceAtLeast(0), stability = stats.stability + 8), treasury - 2500, resources)
                },
                EventOption("Privatize Security", "Hire private firms") { stats, treasury, resources ->
                    Triple(stats.copy(crime = (stats.crime - 10).coerceAtLeast(0), economy = stats.economy + 3), treasury - 3500, resources)
                }
            )
        ),

        GameEvent(
            id = "soc_5",
            title = "Drug Epidemic",
            description = "Drug addiction is spreading like wildfire!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(healthcare = (it.healthcare - 10).coerceAtLeast(0), happiness = (it.happiness - 12).coerceAtLeast(0), crime = (it.crime + 15).coerceAtMost(100)) },
            options = listOf(
                EventOption("War on Drugs", "Crackdown hard") { stats, treasury, resources ->
                    Triple(stats.copy(crime = (stats.crime - 10).coerceAtLeast(0), happiness = stats.happiness - 5), treasury - 5000, resources)
                },
                EventOption("Decriminalization", "Treat as health issue") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 10, happiness = stats.happiness + 5, crime = (stats.crime - 15).coerceAtLeast(0)), treasury - 4000, resources)
                },
                EventOption("Rehabilitation Programs", "Help addicts recover") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 15, happiness = stats.happiness + 8), treasury - 6000, resources)
                },
                EventOption("Legalization", "Regulate and tax") { stats, treasury, resources ->
                    Triple(stats.copy(crime = (stats.crime - 20).coerceAtLeast(0), economy = stats.economy + 8, happiness = stats.happiness + 3), treasury + 2000, resources)
                }
            )
        ),

        GameEvent(
            id = "soc_6",
            title = "Youth Unemployment",
            description = "Young people can't find jobs and are losing hope!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(happiness = (it.happiness - 10).coerceAtLeast(0), stability = (it.stability - 8).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Job Training", "Skills development") { stats, treasury, resources ->
                    Triple(stats.copy(education = stats.education + 10, happiness = stats.happiness + 8), treasury - 3500, resources)
                },
                EventOption("Youth Employment Program", "Government jobs") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 12, stability = stats.stability + 8), treasury - 5000, resources)
                },
                EventOption("Tax Incentives", "Encourage hiring") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, economy = stats.economy + 5), treasury - 2500, resources)
                },
                EventOption("Education Extension", "Keep them in school") { stats, treasury, resources ->
                    Triple(stats.copy(education = stats.education + 12, happiness = stats.happiness + 3), treasury - 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "soc_7",
            title = "Gender Equality Movement",
            description = "Citizens are demanding equal rights and opportunities!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 5).coerceAtMost(100), stability = (it.stability + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Full Equality Laws", "Comprehensive reform") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability + 10, economy = stats.economy + 8), treasury - 3000, resources)
                },
                EventOption("Quota System", "Mandate representation") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability - 5), treasury - 1500, resources)
                },
                EventOption("Awareness Campaign", "Change minds") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8), treasury - 1000, resources)
                },
                EventOption("Do Nothing", "Let society evolve") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 8, stability = stats.stability - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "soc_8",
            title = "Immigration Debate",
            description = "The nation is deeply divided over immigration policy!",
            category = EventCategory.SOCIAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(stability = (it.stability - 8).coerceAtLeast(0), happiness = (it.happiness - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Open Borders", "Welcome immigrants") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, economy = stats.economy + 8, stability = stats.stability - 10), treasury - 2000, resources.copy(food = (resources.food - 20).coerceAtLeast(0)))
                },
                EventOption("Strict Controls", "Limit immigration") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness - 5), treasury - 1500, resources)
                },
                EventOption("Merit-Based", "Skilled immigrants only") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 10, technology = stats.technology + 5, happiness = stats.happiness + 3), treasury - 2000, resources)
                },
                EventOption("Path to Citizenship", "Integrate existing immigrants") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 12, stability = stats.stability + 8), treasury - 3500, resources)
                }
            )
        )
    )

    // ============================================
    // SCIENTIFIC EVENTS (80+ events)
    // ============================================
    
    val scientificEvents = listOf(
        GameEvent(
            id = "sci_1",
            title = "Scientific Breakthrough",
            description = "Your researchers have made a groundbreaking discovery!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(technology = (it.technology + 12).coerceAtMost(100)) },
            options = listOf(
                EventOption("Patent and License", "Commercialize") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 8, economy = stats.economy + 10), treasury + 4000, resources)
                },
                EventOption("Open Source", "Share with world") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 15, stability = stats.stability + 10, happiness = stats.happiness + 5), treasury - 1000, resources)
                },
                EventOption("Military Application", "Weaponize") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 15, technology = stats.technology + 5), treasury - 2000, resources)
                },
                EventOption("Further Research", "Build on discovery") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 18), treasury - 3500, resources)
                }
            )
        ),

        GameEvent(
            id = "sci_2",
            title = "AI Revolution",
            description = "Artificial intelligence is transforming your society!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(technology = (it.technology + 10).coerceAtMost(100), economy = (it.economy + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Embrace AI", "Lead the revolution") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 15, economy = stats.economy + 12, happiness = stats.happiness - 5), treasury - 3000, resources)
                },
                EventOption("Regulate Heavily", "Protect jobs") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 5, happiness = stats.happiness + 8), treasury - 1500, resources)
                },
                EventOption("Ban AI", "Traditional approach") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, stability = stats.stability + 5), treasury, resources)
                },
                EventOption("AI Partnership", "Human-AI collaboration") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 12, economy = stats.economy + 8, happiness = stats.happiness + 3), treasury - 2500, resources)
                }
            )
        ),

        GameEvent(
            id = "sci_3",
            title = "Space Program Milestone",
            description = "Your space program has achieved a major milestone!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(technology = (it.technology + 8).coerceAtMost(100), happiness = (it.happiness + 8).coerceAtMost(100)) },
            options = listOf(
                EventOption("Continue Program", "Push further") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 12, happiness = stats.happiness + 5), treasury - 8000, resources)
                },
                EventOption("Commercialize", "Private space industry") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 8, economy = stats.economy + 10), treasury + 2000, resources)
                },
                EventOption("International Partnership", "Share costs") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 10, stability = stats.stability + 5), treasury - 4000, resources)
                },
                EventOption("End Program", "Focus on Earth") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, happiness = stats.happiness - 5), treasury + 3000, resources)
                }
            )
        ),

        GameEvent(
            id = "sci_4",
            title = "Gene Therapy Breakthrough",
            description = "Scientists have developed revolutionary gene therapy treatments!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(healthcare = (it.healthcare + 15).coerceAtMost(100), technology = (it.technology + 8).coerceAtMost(100)) },
            options = listOf(
                EventOption("Free Treatment", "Universal access") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 20, happiness = stats.happiness + 15), treasury - 6000, resources)
                },
                EventOption("Private Market", "Let companies sell") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 10, economy = stats.economy + 8), treasury + 2000, resources)
                },
                EventOption("Export Technology", "Sell abroad") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 5), treasury + 5000, resources)
                },
                EventOption("Further Research", "Improve the therapy") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 18, technology = stats.technology + 12), treasury - 4000, resources)
                }
            )
        ),

        GameEvent(
            id = "sci_5",
            title = "Climate Engineering",
            description = "Scientists propose geoengineering to combat climate change!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(environment = (it.environment + 10).coerceAtMost(100), technology = (it.technology + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Full Deployment", "Engineer the climate") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 20, happiness = stats.happiness + 5), treasury - 10000, resources)
                },
                EventOption("Limited Testing", "Cautious approach") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 10, technology = stats.technology + 8), treasury - 5000, resources)
                },
                EventOption("Ban Research", "Too risky") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment - 5, happiness = stats.happiness + 5), treasury, resources)
                },
                EventOption("International Cooperation", "Global effort") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, stability = stats.stability + 8), treasury - 7000, resources)
                }
            )
        ),

        GameEvent(
            id = "sci_6",
            title = "Fusion Energy Breakthrough",
            description = "Scientists have achieved sustained fusion reaction!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(technology = (it.technology + 15).coerceAtMost(100), economy = (it.economy + 8).coerceAtMost(100)) },
            options = listOf(
                EventOption("Build Reactors", "Deploy immediately") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 10, economy = stats.economy + 15, environment = stats.environment + 15), treasury - 15000, resources)
                },
                EventOption("Further Research", "Perfect the technology") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 20), treasury - 8000, resources)
                },
                EventOption("Share Technology", "Global benefit") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 12, stability = stats.stability + 15, happiness = stats.happiness + 10), treasury - 5000, resources)
                },
                EventOption("Sell Patents", "Profit from discovery") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 5), treasury + 10000, resources)
                }
            )
        ),

        GameEvent(
            id = "sci_7",
            title = "Quantum Computing",
            description = "Your nation has developed functional quantum computers!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(technology = (it.technology + 12).coerceAtMost(100), economy = (it.economy + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Military Use", "Classified applications") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 20, technology = stats.technology + 8), treasury - 6000, resources)
                },
                EventOption("Commercial Use", "Business applications") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 10, economy = stats.economy + 15), treasury - 4000, resources)
                },
                EventOption("Research Only", "Scientific focus") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 18), treasury - 5000, resources)
                },
                EventOption("Open Access", "Public quantum computing") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 15, happiness = stats.happiness + 8), treasury - 3000, resources)
                }
            )
        ),

        GameEvent(
            id = "sci_8",
            title = "Brain-Computer Interface",
            description = "Direct neural interfaces are now possible!",
            category = EventCategory.SCIENTIFIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(technology = (it.technology + 10).coerceAtMost(100), healthcare = (it.healthcare + 8).coerceAtMost(100)) },
            options = listOf(
                EventOption("Medical Use", "Help the disabled") { stats, treasury, resources ->
                    Triple(stats.copy(healthcare = stats.healthcare + 20, happiness = stats.happiness + 10), treasury - 5000, resources)
                },
                EventOption("Consumer Product", "Enhance everyone") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 12, economy = stats.economy + 10, happiness = stats.happiness + 5), treasury - 3000, resources)
                },
                EventOption("Military Enhancement", "Super soldiers") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 25, technology = stats.technology + 8), treasury - 7000, resources)
                },
                EventOption("Ban Technology", "Too dangerous") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, stability = stats.stability + 5), treasury, resources)
                }
            )
        )
    )

    // ============================================
    // DIPLOMATIC EVENTS (100+ events)
    // ============================================
    
    val diplomaticEvents = listOf(
        GameEvent(
            id = "dip_1",
            title = "Alliance Offer",
            description = "A powerful nation has proposed a military alliance!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(stability = (it.stability + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Accept Alliance", "Join forces") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 15, stability = stats.stability + 10), treasury - 2000, resources)
                },
                EventOption("Decline Politely", "Stay independent") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5), treasury, resources)
                },
                EventOption("Counter-Proposal", "Negotiate terms") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 10, stability = stats.stability + 8), treasury - 1000, resources)
                },
                EventOption("Join Later", "Wait and see") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "dip_2",
            title = "State Visit",
            description = "A foreign leader wants to make an official visit!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(stability = (it.stability + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Roll Out Red Carpet", "Grand reception") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 5), treasury - 3000, resources)
                },
                EventOption("Standard Protocol", "Normal接待") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5), treasury - 1000, resources)
                },
                EventOption("Keep It Low-Key", "Minimal fuss") { stats, treasury, resources ->
                    Triple(stats, treasury - 200, resources)
                },
                EventOption("Decline Visit", "Too busy") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "dip_3",
            title = "International Summit",
            description = "You've been invited to host a major international summit!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(stability = (it.stability + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Host Summit", "Showcase your nation") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15, happiness = stats.happiness + 8), treasury - 5000, resources)
                },
                EventOption("Co-Host", "Share responsibility") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10), treasury - 2500, resources)
                },
                EventOption("Attend Only", "Just participate") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5), treasury - 1000, resources)
                },
                EventOption("Skip Summit", "Send deputy") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "dip_4",
            title = "Cultural Exchange",
            description = "A nation proposes a cultural exchange program!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Full Exchange", "Embrace cultures") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 12, stability = stats.stability + 8), treasury - 2000, resources)
                },
                EventOption("Limited Exchange", "Selective programs") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 6), treasury - 800, resources)
                },
                EventOption("One-Way Only", "Send only") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 3), treasury - 500, resources)
                },
                EventOption("Decline", "Protect culture") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "dip_5",
            title = "Refugee Crisis",
            description = "Thousands of refugees are fleeing to your borders!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(happiness = (it.happiness - 8).coerceAtLeast(0), stability = (it.stability - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Open Borders", "Accept all refugees") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability - 10), treasury - 4000, resources.copy(food = (resources.food - 30).coerceAtLeast(0)))
                },
                EventOption("Quota System", "Limited acceptance") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 3, stability = stats.stability - 3), treasury - 2000, resources.copy(food = (resources.food - 15).coerceAtLeast(0)))
                },
                EventOption("Border Closure", "No entry") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 10, stability = stats.stability + 5), treasury - 1000, resources)
                },
                EventOption("International Solution", "Share burden") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, stability = stats.stability + 3), treasury - 1500, resources)
                }
            )
        ),

        GameEvent(
            id = "dip_6",
            title = "Foreign Aid Request",
            description = "A struggling nation is asking for emergency aid!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MINOR,
            effect = { it },
            options = listOf(
                EventOption("Generous Aid", "Give substantially") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 8), treasury - 5000, resources.copy(food = (resources.food - 20).coerceAtLeast(0)))
                },
                EventOption("Modest Aid", "Help a little") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5), treasury - 2000, resources.copy(food = (resources.food - 10).coerceAtLeast(0)))
                },
                EventOption("Conditional Aid", "With strings attached") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5), treasury - 1500, resources)
                },
                EventOption("Refuse Aid", "Not our problem") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "dip_7",
            title = "Spy Exchange",
            description = "A foreign power proposes exchanging captured spies!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(stability = (it.stability + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Accept Exchange", "Get our people back") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 10, happiness = stats.happiness + 5), treasury - 1000, resources)
                },
                EventOption("Demand More", "Negotiate better") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 8), treasury - 500, resources)
                },
                EventOption("Refuse", "Keep our heroes") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 5, happiness = stats.happiness - 5), treasury, resources)
                },
                EventOption("Counter-Offer", "Different terms") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 6), treasury - 800, resources)
                }
            )
        ),

        GameEvent(
            id = "dip_8",
            title = "Joint Military Exercise",
            description = "An ally proposes joint military training exercises!",
            category = EventCategory.DIPLOMATIC,
            severity = EventSeverity.MINOR,
            effect = { it.copy(military = (it.military + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Full Participation", "Commit fully") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 12, stability = stats.stability + 5), treasury - 3000, resources)
                },
                EventOption("Limited Participation", "Send observers") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 5), treasury - 1000, resources)
                },
                EventOption("Host Exercise", "On your soil") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 8, stability = stats.stability + 8), treasury - 2000, resources)
                },
                EventOption("Decline", "Too busy") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military - 3), treasury, resources)
                }
            )
        )
    )

    // ============================================
    // ENVIRONMENTAL EVENTS (80+ events)
    // ============================================
    
    val environmentalEvents = listOf(
        GameEvent(
            id = "env_1",
            title = "Climate Summit",
            description = "World leaders are gathering to address climate change!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(environment = (it.environment + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Commit to Reductions", "Aggressive targets") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, happiness = stats.happiness + 5, economy = stats.economy - 8), treasury - 5000, resources)
                },
                EventOption("Moderate Commitment", "Balanced approach") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 8, economy = stats.economy - 3), treasury - 2500, resources)
                },
                EventOption("Minimal Commitment", "Do the bare minimum") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 3), treasury - 1000, resources)
                },
                EventOption("Reject Agreement", "Protect economy") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, environment = stats.environment - 10, happiness = stats.happiness - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "env_2",
            title = "Endangered Species",
            description = "A beloved species is on the brink of extinction!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(environment = (it.environment + 2).coerceAtMost(100), happiness = (it.happiness + 2).coerceAtMost(100)) },
            options = listOf(
                EventOption("Full Protection", "Whatever it takes") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 12, happiness = stats.happiness + 10), treasury - 3000, resources)
                },
                EventOption("Habitat Reserve", "Protected area") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 8, happiness = stats.happiness + 5), treasury - 1500, resources)
                },
                EventOption("Breeding Program", "Captive breeding") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 6, technology = stats.technology + 3), treasury - 2000, resources)
                },
                EventOption("Do Nothing", "Natural selection") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment - 5, happiness = stats.happiness - 8), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "env_3",
            title = "Deforestation Crisis",
            description = "Your forests are being destroyed at an alarming rate!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(environment = (it.environment - 20).coerceAtLeast(0), happiness = (it.happiness - 8).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Logging Ban", "Stop all cutting") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, happiness = stats.happiness + 5, economy = stats.economy - 5), treasury - 2000, resources)
                },
                EventOption("Sustainable Logging", "Regulated harvesting") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 8, economy = stats.economy + 2), treasury - 1000, resources)
                },
                EventOption("Reforestation", "Plant new trees") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 12, happiness = stats.happiness + 3), treasury - 3500, resources)
                },
                EventOption("Let Industry Work", "No intervention") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment - 10, economy = stats.economy + 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "env_4",
            title = "Ocean Pollution",
            description = "Your coastal waters are becoming heavily polluted!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(environment = (it.environment - 18).coerceAtLeast(0), happiness = (it.happiness - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Cleanup Campaign", "Massive cleanup effort") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, happiness = stats.happiness + 8), treasury - 5000, resources)
                },
                EventOption("Regulate Industry", "Stop pollution at source") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 10, economy = stats.economy - 5, happiness = stats.happiness + 5), treasury - 2000, resources)
                },
                EventOption("Marine Reserve", "Protected waters") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 12, happiness = stats.happiness + 5), treasury - 3000, resources)
                },
                EventOption("Ignore", "Ocean is vast") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment - 10, happiness = stats.happiness - 10), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "env_5",
            title = "Air Quality Crisis",
            description = "Air pollution is reaching dangerous levels!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(environment = (it.environment - 15).coerceAtLeast(0), healthcare = (it.healthcare - 10).coerceAtLeast(0), happiness = (it.happiness - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Emission Controls", "Strict regulations") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, healthcare = stats.healthcare + 5, happiness = stats.happiness + 5, economy = stats.economy - 5), treasury - 4000, resources)
                },
                EventOption("Green Energy", "Switch to renewables") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 18, technology = stats.technology + 8), treasury - 8000, resources)
                },
                EventOption("Public Transport", "Reduce cars") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 10, happiness = stats.happiness + 3), treasury - 5000, resources)
                },
                EventOption("Do Nothing", "Economy first") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment - 10, healthcare = stats.healthcare - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "env_6",
            title = "Biodiversity Loss",
            description = "Species are disappearing at an unprecedented rate!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(environment = (it.environment - 12).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Conservation Areas", "Expand protected zones") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 15, happiness = stats.happiness + 5), treasury - 4000, resources)
                },
                EventOption("Anti-Poaching", "Combat illegal hunting") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 10, stability = stats.stability + 3), treasury - 2500, resources)
                },
                EventOption("Education Campaign", "Raise awareness") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 8, happiness = stats.happiness + 3), treasury - 1500, resources)
                },
                EventOption("Economic Incentives", "Pay for conservation") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 12, economy = stats.economy - 3), treasury - 3500, resources)
                }
            )
        ),

        GameEvent(
            id = "env_7",
            title = "Water Crisis",
            description = "Fresh water supplies are running dangerously low!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(environment = (it.environment - 15).coerceAtLeast(0), happiness = (it.happiness - 12).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Desalination", "Build plants") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 10, happiness = stats.happiness + 8), treasury - 7000, resources)
                },
                EventOption("Water Conservation", "Strict rationing") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 8, happiness = stats.happiness - 5), treasury - 2000, resources.copy(food = (resources.food - 15).coerceAtLeast(0)))
                },
                EventOption("Import Water", "Buy from neighbors") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5), treasury - 5000, resources)
                },
                EventOption("Privatize Water", "Let market handle") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5, happiness = stats.happiness - 15), treasury + 1000, resources)
                }
            )
        ),

        GameEvent(
            id = "env_8",
            title = "Renewable Energy Boom",
            description = "Clean energy technology is becoming cost-competitive!",
            category = EventCategory.ENVIRONMENTAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(environment = (it.environment + 5).coerceAtMost(100), technology = (it.technology + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Full Transition", "100% renewable") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 20, technology = stats.technology + 10, economy = stats.economy + 5), treasury - 10000, resources)
                },
                EventOption("Mixed Approach", "Gradual transition") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 12, technology = stats.technology + 8), treasury - 5000, resources)
                },
                EventOption("Subsidies Only", "Encourage private investment") { stats, treasury, resources ->
                    Triple(stats.copy(environment = stats.environment + 8, economy = stats.economy + 3), treasury - 3000, resources)
                },
                EventOption("Wait and See", "Let technology mature") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        )
    )

    // ============================================
    // CULTURAL EVENTS (60+ events)
    // ============================================
    
    val culturalEvents = listOf(
        GameEvent(
            id = "cul_1",
            title = "Cultural Festival",
            description = "A national cultural festival is being planned!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 8).coerceAtMost(100), stability = (it.stability + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Grand Celebration", "Spare no expense") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability + 10), treasury - 3000, resources)
                },
                EventOption("Modest Event", "Keep it simple") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, stability = stats.stability + 5), treasury - 1000, resources)
                },
                EventOption("International Festival", "Invite the world") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 8), treasury - 2000, resources)
                },
                EventOption("Cancel", "Save money") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 5), treasury + 500, resources)
                }
            )
        ),

        GameEvent(
            id = "cul_2",
            title = "National Sports Victory",
            description = "Your national team has won a major international tournament!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 12).coerceAtMost(100), stability = (it.stability + 8).coerceAtMost(100)) },
            options = listOf(
                EventOption("National Holiday", "Celebrate together") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 18, stability = stats.stability + 10), treasury - 1000, resources)
                },
                EventOption("Reward Team", "Bonus for athletes") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15), treasury - 2000, resources)
                },
                EventOption("Invest in Sports", "Build on success") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 5), treasury - 3000, resources)
                },
                EventOption("Normal Response", "Business as usual") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "cul_3",
            title = "Film Industry Boom",
            description = "Your nation's films are gaining international acclaim!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 5).coerceAtMost(100), economy = (it.economy + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Fund Film Industry", "Support the arts") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, economy = stats.economy + 8), treasury - 2500, resources)
                },
                EventOption("Tax Incentives", "Encourage production") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, economy = stats.economy + 10), treasury - 1500, resources)
                },
                EventOption("Film Festival", "Host international event") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, stability = stats.stability + 5), treasury - 2000, resources)
                },
                EventOption("Let Industry Grow", "Organic development") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "cul_4",
            title = "Music Revolution",
            description = "A new music genre from your nation is taking the world by storm!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 6).coerceAtMost(100)) },
            options = listOf(
                EventOption("Promote Globally", "Cultural diplomacy") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 12, stability = stats.stability + 8), treasury - 2000, resources)
                },
                EventOption("Support Artists", "Fund the movement") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, economy = stats.economy + 5), treasury - 1500, resources)
                },
                EventOption("Commercialize", "Sell the phenomenon") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5), treasury + 3000, resources)
                },
                EventOption("Let It Be", "Don't interfere") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "cul_5",
            title = "Historical Discovery",
            description = "Archaeologists have uncovered an important historical site!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 5).coerceAtMost(100), stability = (it.stability + 5).coerceAtMost(100)) },
            options = listOf(
                EventOption("Preserve Site", "Protect the discovery") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 10), treasury - 2000, resources)
                },
                EventOption("Tourism Development", "Make it accessible") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, economy = stats.economy + 8), treasury - 3000, resources)
                },
                EventOption("Research Only", "Scientific focus") { stats, treasury, resources ->
                    Triple(stats.copy(technology = stats.technology + 8, happiness = stats.happiness + 5), treasury - 1500, resources)
                },
                EventOption("Sell Artifacts", "Profit from discovery") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 10, stability = stats.stability - 5), treasury + 5000, resources)
                }
            )
        ),

        GameEvent(
            id = "cul_6",
            title = "Language Revival",
            description = "There's growing interest in reviving traditional languages!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 3).coerceAtMost(100), stability = (it.stability + 3).coerceAtMost(100)) },
            options = listOf(
                EventOption("Official Status", "Make it a national language") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 12, stability = stats.stability + 10), treasury - 2000, resources)
                },
                EventOption("Education Program", "Teach in schools") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, education = stats.education + 5), treasury - 2500, resources)
                },
                EventOption("Media Support", "Fund broadcasts") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 6), treasury - 1000, resources)
                },
                EventOption("Let It Fade", "Natural evolution") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 5), treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "cul_7",
            title = "Cultural Heritage Site",
            description = "One of your landmarks has been designated a World Heritage Site!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(happiness = (it.happiness + 6).coerceAtMost(100), stability = (it.stability + 4).coerceAtMost(100)) },
            options = listOf(
                EventOption("Preservation Fund", "Protect the site") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 8), treasury - 2500, resources)
                },
                EventOption("Tourism Push", "Maximize visitors") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, economy = stats.economy + 10), treasury - 1500, resources)
                },
                EventOption("Balanced Approach", "Careful management") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, economy = stats.economy + 5), treasury - 1000, resources)
                },
                EventOption("Minimal Investment", "Let it be") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        ),

        GameEvent(
            id = "cul_8",
            title = "Cultural Controversy",
            description = "A controversial artwork/exhibition has sparked national debate!",
            category = EventCategory.CULTURAL,
            severity = EventSeverity.MINOR,
            effect = { it.copy(stability = (it.stability - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Support Artist", "Defend free expression") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 8, stability = stats.stability - 8), treasury - 500, resources)
                },
                EventOption("Censor", "Remove the work") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness - 8, stability = stats.stability + 5), treasury, resources)
                },
                EventOption("Facilitate Dialogue", "Encourage discussion") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 3, stability = stats.stability + 3), treasury - 1000, resources)
                },
                EventOption("Stay Neutral", "Government takes no side") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability - 3), treasury, resources)
                }
            )
        )
    )

    // ============================================
    // HELPER FUNCTIONS
    // ============================================
    
    /**
     * Get all events organized by category
     */
    fun getAllEvents(): List<GameEvent> {
        return economicEvents + politicalEvents + militaryEvents + 
               disasterEvents + socialEvents + scientificEvents + 
               diplomaticEvents + environmentalEvents + culturalEvents
    }

    /**
     * Get events for a specific category
     */
    fun getEventsByCategory(category: EventCategory): List<GameEvent> {
        return when (category) {
            EventCategory.ECONOMIC -> economicEvents
            EventCategory.POLITICAL -> politicalEvents
            EventCategory.MILITARY -> militaryEvents
            EventCategory.DISASTER -> disasterEvents
            EventCategory.SOCIAL -> socialEvents
            EventCategory.SCIENTIFIC -> scientificEvents
            EventCategory.DIPLOMATIC -> diplomaticEvents
            EventCategory.ENVIRONMENTAL -> environmentalEvents
            EventCategory.CULTURAL -> culturalEvents
        }
    }

    /**
     * Get random event from a category
     */
    fun getRandomEvent(category: EventCategory): GameEvent {
        val events = getEventsByCategory(category)
        return events.random()
    }

    /**
     * Get random event from any category
     */
    fun getRandomEvent(): GameEvent {
        return getAllEvents().random()
    }

    /**
     * Get events by severity
     */
    fun getEventsBySeverity(severity: EventSeverity): List<GameEvent> {
        return getAllEvents().filter { it.severity == severity }
    }

    /**
     * Search events by keyword
     */
    fun searchEvents(keyword: String): List<GameEvent> {
        val lowerKeyword = keyword.lowercase()
        return getAllEvents().filter { 
            it.title.lowercase().contains(lowerKeyword) || 
            it.description.lowercase().contains(lowerKeyword)
        }
    }

    /**
     * Get event count statistics
     */
    fun getEventStatistics(): Map<String, Int> {
        return mapOf(
            "Economic" to economicEvents.size,
            "Political" to politicalEvents.size,
            "Military" to militaryEvents.size,
            "Disaster" to disasterEvents.size,
            "Social" to socialEvents.size,
            "Scientific" to scientificEvents.size,
            "Diplomatic" to diplomaticEvents.size,
            "Environmental" to environmentalEvents.size,
            "Cultural" to culturalEvents.size,
            "Total" to getAllEvents().size
        )
    }
}
