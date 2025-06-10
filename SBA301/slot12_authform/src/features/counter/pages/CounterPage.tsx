import React from "react";
import "../styles/Counter.css";
import { useCounter } from "../counterSlice";
import CounterDisplay from "../components/CounterDisplay";
import CounterButtons from "../components/CounterButtons";
import CalculatorInput from "../components/CalculatorInput";
import HistorySection from "../components/HistorySection";

const CounterPage: React.FC = () => {
  const {
    count,
    history,
    increment,
    decrement,
    add,
    subtract,
    multiply,
    divide,
    reset,
    clearHistory,
  } = useCounter(0);

  const handleCalculation = (operation: string, value: number) => {
    switch (operation) {
      case "add":
        add(value);
        break;
      case "subtract":
        subtract(value);
        break;
      case "multiply":
        multiply(value);
        break;
      case "divide":
        divide(value);
        break;
      default:
        break;
    }
  };

  return (
    <div className="counter-container">
      <h1>Counter Calculator</h1>

      <CounterDisplay count={count} />

      <CounterButtons
        increment={increment}
        decrement={decrement}
        reset={reset}
      />

      <CalculatorInput onCalculate={handleCalculation} />

      <HistorySection history={history} clearHistory={clearHistory} />
    </div>
  );
};

export default CounterPage;
