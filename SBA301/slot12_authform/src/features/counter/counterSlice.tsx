import { useState } from "react";

// Define the hook
export const useCounter = (initialValue = 0) => {
  const [count, setCount] = useState(initialValue);
  const [history, setHistory] = useState<string[]>([]);

  // Basic counter operations
  const increment = () => {
    setCount((prevCount) => {
      const newCount = prevCount + 1;
      addToHistory(`${prevCount} + 1 = ${newCount}`);
      return newCount;
    });
  };

  const decrement = () => {
    setCount((prevCount) => {
      const newCount = prevCount - 1;
      addToHistory(`${prevCount} - 1 = ${newCount}`);
      return newCount;
    });
  };

  // Calculator operations
  const add = (value: number) => {
    setCount((prevCount) => {
      const newCount = prevCount + value;
      addToHistory(`${prevCount} + ${value} = ${newCount}`);
      return newCount;
    });
  };

  const subtract = (value: number) => {
    setCount((prevCount) => {
      const newCount = prevCount - value;
      addToHistory(`${prevCount} - ${value} = ${newCount}`);
      return newCount;
    });
  };

  const multiply = (value: number) => {
    setCount((prevCount) => {
      const newCount = prevCount * value;
      addToHistory(`${prevCount} × ${value} = ${newCount}`);
      return newCount;
    });
  };

  const divide = (value: number) => {
    if (value === 0) {
      addToHistory(`Cannot divide by zero`);
      return;
    }

    setCount((prevCount) => {
      const newCount = prevCount / value;
      addToHistory(`${prevCount} ÷ ${value} = ${newCount}`);
      return newCount;
    });
  };

  const reset = () => {
    setCount(initialValue);
    addToHistory("Counter reset to initial value");
  };

  // Helper to add operations to history
  const addToHistory = (operation: string) => {
    setHistory((prev) => [...prev, operation]);
  };

  const clearHistory = () => {
    setHistory([]);
  };

  return {
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
  };
};
