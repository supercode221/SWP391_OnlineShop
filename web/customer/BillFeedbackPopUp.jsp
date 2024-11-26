<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="product-feedback-popup">
    <div class="product-feedback-header d-flex justify-content-between align-items-center p-3">
        <h5 class="m-0 text-center flex-grow-1">Freeze Feedback - Bill #${bill.id}</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
    </div>
    <div class="product-feedback-body p-3">
        <div class="bill-infor">
            <p>Feedback for ours shop, high star rate, high services include !</p>
            <p class='text-center'><strong>Total Price: <fmt:formatNumber value="${requestScope.bill.totalPrice}" pattern="###,###,###VND"/></strong></p>
        </div>
    </div>
    <div class="product-feedback-body p-3">
        <div class="image-upload-container">
            <div class="image-upload-wrapper">
                <input class="image-upload-main" type="file" accept="image/*">
            </div>
            <div class="image-upload-wrapper">
                <input class="image-upload-main" type="file" accept="image/*">
            </div>
            <div class="image-upload-wrapper">
                <input class="image-upload-main" type="file" accept="image/*">
            </div>
            <div class="image-upload-wrapper">
                <input class="image-upload-main" type="file" accept="image/*">
            </div>
        </div>
        <div class="star-section">
            <div class="star-slogan text-center mb-3">
                <h5>Rate me 5 star !</h5>
            </div>
            <div class="star">
                <div class="star-wrapper">
                    <i class="fa-solid fa-star fa-2xl"></i>
                </div>
                <div class="star-wrapper">
                    <i class="fa-solid fa-star fa-2xl"></i>
                </div>
                <div class="star-wrapper">
                    <i class="fa-solid fa-star fa-2xl"></i>
                </div>
                <div class="star-wrapper">
                    <i class="fa-solid fa-star fa-2xl"></i>
                </div>
                <div class="star-wrapper">
                    <i class="fa-solid fa-star fa-2xl"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="product-feedback-body p-3">
        <div class="comment-section">
            <textarea  oninput="handleCommentChange()" id="comment" placeholder="Write your thought ..."></textarea>
            <p class="text-right text-end" id='charCountMain'><span id="charCount">0</span>/100</p>
        </div>
    </div>
    <div class="product-feedback-footer d-flex justify-content-center pb-5">
        <div class="d-flex flex-column align-items-center">
            <p class="warning-text mb-2" id="msg"></p>
            <button id='bill-feedback-sent' onclick="getBillDataFeedback()">Thanks For Your Feedback</button>
        </div>
    </div>
</div>
