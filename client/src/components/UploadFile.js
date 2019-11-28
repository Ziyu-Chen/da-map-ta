import React from "react";
import { connect } from "react-redux";
import { getColumns } from "../redux/actions";
import "./UploadFile.css";

function UploadFile(props) {
  function handleClick() {
    document.getElementById("file").click();
  }
  return (
    <div className="UploadFile">
      <input
        className="file-input"
        type="file"
        name="file"
        id="file"
        onChange={e => props.getColumns(e.target.files[0])}
      />
      <button type="button" onClick={handleClick}>
        Choose your Geolocation data
      </button>
    </div>
  );
}
const mapDispatchToProps = dispatch => ({
  getColumns: file => dispatch(getColumns(file))
});

export default connect(null, mapDispatchToProps)(UploadFile);
