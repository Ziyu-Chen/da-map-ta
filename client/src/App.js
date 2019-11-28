import React from "react";
import "./App.css";
import MapContainer from "./components/MapContainer";
import UploadFile from "./components/UploadFile";
import Columns from "./components/Columns";

function App() {
  return (
    <div className="App">
      <UploadFile />
      <Columns />
      <MapContainer />
    </div>
  );
}

export default App;
