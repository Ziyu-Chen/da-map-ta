import React from "react";
import { connect } from "react-redux";
import { selectColumn, getTable } from "../redux/actions";
import "./Columns.css";

function Columns(props) {
  return (
    <div className="columns">
      {props.columns.length > 0 ? (
        <div>
          <div>
            Please select the information you need in addition to latitude and
            longtitude
          </div>
          {props.columns.map((column, index) =>
            column.toLowerCase().startsWith("lat") ||
            column.toLowerCase().startsWith("lon") ||
            column.toLowerCase().startsWith("lng") ? (
              <div></div>
            ) : (
              <div className="column">
                <label className="column-label" for="column">
                  {column}
                </label>
                <input
                  className="column-input"
                  type="checkbox"
                  name="column"
                  id={index}
                  key={index}
                  value={index}
                  onClick={e => props.selectColumn(e.target.value)}
                />
              </div>
            )
          )}
          <button
            type="button"
            onClick={() => props.getTable(props.selectedColumns)}
          >
            Submit
          </button>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
}

const mapStateToProps = state => ({
  columns: state.columns,
  selectedColumns: state.selectedColumns
});

const mapDispatchToProps = dispatch => ({
  selectColumn: num => dispatch(selectColumn(num)),
  getTable: selectedColumns => {
    let cols = selectedColumns
      .map((value, index) => {
        if (value) return index;
        return null;
      })
      .filter(value => value !== null)
      .join(":");
    const file = document.getElementById("file").files[0];
    return dispatch(getTable(file, cols));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(Columns);
