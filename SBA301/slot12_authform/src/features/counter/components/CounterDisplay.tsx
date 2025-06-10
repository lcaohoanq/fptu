import React from "react";

interface CounterDisplayProps {
  count: number;
}

const CounterDisplay: React.FC<CounterDisplayProps> = ({ count }) => {
  return (
    <div className="counter-display">
      <h2>{count}</h2>
    </div>
  );
};

export default CounterDisplay;
