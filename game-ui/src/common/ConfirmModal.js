import React from "react";

export default function ConfirmModal(props) {
    return (
        <div className="modal fade" id={props.id} tabIndex="-1" role="dialog"
             aria-hidden="true">
            <div className="modal-dialog" role="document">
                <div className="modal-content">
                    <div className="modal-body text-center border-bottom">
                        <h5 className="modal-title">{props.title}</h5>
                    </div>
                    <div className="modal-body">
                        <button type="button" className="btn bg-white border border-success m-2"
                                data-dismiss="modal">{props.cancel}
                        </button>
                        <button type="button" className="btn bg-white border border-danger m-2"
                                data-dismiss="modal" onClick={props.handleOk}>{props.ok}
                        </button>
                    </div>
                    <div className="modal-footer">
                    </div>
                </div>
            </div>
        </div>
    );
}