import React from "react";

interface HistorySectionProps {
  history: string[];
  clearHistory: () => void;
}

const HistorySection: React.FC<HistorySectionProps> = ({
  history,
  clearHistory,
}) => {
  return (
    <div className="history-section">
      <div className="history-header">
        <h3>Operation History</h3>
        <button onClick={clearHistory} className="btn-clear">
          Clear
        </button>
      </div>

      <div className="history-list">
        {history.length > 0 ? (
          <ul>
            {history.map((item, index) => (
              <li key={index}>{item}</li>
            ))}
          </ul>
        ) : (
          <p className="no-history">No operations performed yet</p>
        )}
      </div>
    </div>
  );
};

export default HistorySection;
