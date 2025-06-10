import React, { useState } from "react";

interface CalculatorInputProps {
  onCalculate: (operation: string, value: number) => void;
}

const CalculatorInput: React.FC<CalculatorInputProps> = ({ onCalculate }) => {
  const [inputValue, setInputValue] = useState<string>("");
  const [operation, setOperation] = useState<string>("add");

  const handleCalculate = () => {
    const value = Number(inputValue);

    if (isNaN(value)) {
      alert("Please enter a valid number");
      return;
    }

    onCalculate(operation, value);
    setInputValue("");
  };

  return (
    <div className="calculator">
      <div className="calculator-inputs">
        <select
          value={operation}
          onChange={(e) => setOperation(e.target.value)}
          className="operation-select"
        >
          <option value="add">+</option>
          <option value="subtract">-</option>
          <option value="multiply">×</option>
          <option value="divide">÷</option>
        </select>

        <input
          type="text"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          placeholder="Enter a number"
          className="number-input"
        />

        <button onClick={handleCalculate} className="btn-calculate">
          Calculate
        </button>
      </div>
    </div>
  );
};

export default CalculatorInput;
