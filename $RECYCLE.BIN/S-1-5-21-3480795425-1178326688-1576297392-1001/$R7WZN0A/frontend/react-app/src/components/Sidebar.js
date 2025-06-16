import React from 'react';
import { FaCloud, FaUpload, FaFolder, FaStar, FaShareAlt } from 'react-icons/fa';

const menu = [
  { icon: <FaCloud />, label: 'My Cloud' },
  { icon: <FaFolder />, label: 'Documents' },
  { icon: <FaStar />, label: 'Favorites' },
  { icon: <FaShareAlt />, label: 'Shared' },
  { icon: <FaUpload />, label: 'Upload' },
];

export default function Sidebar() {
  return (
    <div className="w-64 bg-white shadow-lg p-4 space-y-6">
      <h1 className="text-xl font-bold">File Manager</h1>
      <ul className="space-y-4">
        {menu.map((item, index) => (
          <li key={index} className="flex items-center space-x-2 hover:text-blue-500 cursor-pointer">
            <span>{item.icon}</span>
            <span>{item.label}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}