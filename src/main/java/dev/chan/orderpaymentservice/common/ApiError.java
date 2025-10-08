package dev.chan.orderpaymentservice.common;

public record ApiError(int status, String code, String message) {

}
