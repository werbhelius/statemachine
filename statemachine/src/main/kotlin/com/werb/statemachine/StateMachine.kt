package com.werb.statemachine

/**
 * Created by wanbo on 2018/6/11.
 */
class StateMachine<State, Transition> {

    private val statesMap = mutableMapOf<State, () -> Unit>()
    private val transitionsMap = mutableMapOf<State, MutableMap<Transition, State>>()

    var lastTransition: Transition? = null
        private set
    var lastState: State? = null
        private set

    private var currentState: State? = null
        set(value) {
            field = value
            statesMap[value]?.invoke()
        }

    fun initialState(state: State) {
        currentState = state
    }

    fun initState(state: State, block: () -> Unit) {
        statesMap[state] = block
    }

    fun addState(transition: Transition, fromState: State, toState: State) {
        val bag = transitionsMap[fromState] ?: mutableMapOf()
        bag[transition] = toState
        transitionsMap[fromState] = bag
    }

    fun executeTransition(transition: Transition) {
        val fromState = currentState ?: return
        val toState = transitionsMap[fromState]?.get(transition) ?: return
        lastState = fromState
        lastTransition = transition
        currentState = toState
    }

}