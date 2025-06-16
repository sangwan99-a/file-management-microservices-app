import React from 'react';

export default function Dashboard() {
  return (
    <div>
      <h2 className="text-2xl font-semibold mb-4">Recent Files</h2>
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        {[1, 2, 3, 4].map((n) => (
          <div key={n} className="bg-white p-4 rounded-xl shadow-md">
            <p className="text-sm">File {n}</p>
          </div>
        ))}
      </div>
    </div>
  );
}