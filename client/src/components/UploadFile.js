import React from "react";
import { connect } from "react-redux";
import { getColumns } from "../redux/actions";

function UploadFile(props) {
  function handleClick() {
    document.getElementById("file").click();
  }
  return (
    <div className="UploadFile">
      <label for="file">Upload your Geolocation data</label>
      <input
        type="file"
        name="file"
        id="file"
        onChange={e => props.getColumns(e.target.files[0])}
      />
      <button type="button" onClick={handleClick}>
        Submit
      </button>
    </div>
  );
}
const mapDispatchToProps = dispatch => ({
  getColumns: file => dispatch(getColumns(file))
});

export default connect(null, mapDispatchToProps)(UploadFile);
