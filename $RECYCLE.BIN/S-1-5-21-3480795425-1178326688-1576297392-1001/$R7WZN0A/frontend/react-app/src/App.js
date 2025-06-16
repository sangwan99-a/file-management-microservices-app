import React from 'react';
import Sidebar from './components/Sidebar';
import Dashboard from './components/Dashboard';

export default function App() {
  return (
    <div className="flex h-screen">
      <Sidebar />
      <div className="flex-1 p-4 overflow-y-auto">
        <Dashboard />
      </div>
    </div>
  );
}