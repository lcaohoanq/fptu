import React from "react";

interface CounterButtonsProps {
  increment: () => void;
  decrement: () => void;
  reset: () => void;
}

const CounterButtons: React.FC<CounterButtonsProps> = ({
  increment,
  decrement,
  reset,
}) => {
  return (
    <div className="counter-buttons">
      <button onClick={decrement} className="btn-action">
        -1
      </button>
      <button onClick={increment} className="btn-action">
        +1
      </button>
      <button onClick={reset} className="btn-reset">
        Reset
      </button>
    </div>
  );
};

export default CounterButtons;
