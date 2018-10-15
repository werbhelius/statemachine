package com.werb.statemachine

import org.junit.Test

/**
 * Created by wanbo on 2018/9/11.
 */
class StateMachineTest {

    enum class State { Solid, Liquid, Gas }

    enum class Transition { OnMelted, OnFroze, OnVaporized, OnCondensed }

    private val stateMachine: StateMachine<State, Transition> by lazy {
        StateMachine<State, Transition>().apply {

            state(State.Solid) {
                lastState?.apply {
                    println("I have been ${State.Solid} from $this by $lastTransition")
                } ?: with(State.Solid) {
                    println("I have been initialState with $this")
                }
            }

            state(State.Liquid) {
                lastState?.apply {
                    println("I have been ${State.Liquid} from $this by $lastTransition")
                } ?: with(State.Liquid) {
                    println("I have been initialState with $this")
                }
            }

            state(State.Gas) {
                lastState?.apply {
                    println("I have been ${State.Gas} from $this by $lastTransition")
                } ?: with(State.Gas) {
                    println("I have been initialState with $this")
                }
            }

            addTransition(State.Solid, Transition.OnMelted, State.Liquid)
            addTransition(State.Liquid, Transition.OnFroze, State.Solid)
            addTransition(State.Liquid, Transition.OnVaporized, State.Gas)
            addTransition(State.Gas, Transition.OnCondensed, State.Liquid)
        }
    }

    @Test
    fun initialState() {
        stateMachine.initialState(State.Solid)
    }

    @Test
    fun solid_onMelted_to_liquid() {
        stateMachine.initialState(State.Solid)
        stateMachine.executeTransition(Transition.OnMelted)
    }

    @Test
    fun liquid_onFroze_to_solid() {
        stateMachine.initialState(State.Liquid)
        stateMachine.executeTransition(Transition.OnFroze)
    }

    @Test
    fun liquid_onVaporized_to_gas() {
        stateMachine.initialState(State.Liquid)
        stateMachine.executeTransition(Transition.OnVaporized)
    }

    @Test
    fun gas_onCondensed_to_liquid() {
        stateMachine.initialState(State.Gas)
        stateMachine.executeTransition(Transition.OnCondensed)
    }

}