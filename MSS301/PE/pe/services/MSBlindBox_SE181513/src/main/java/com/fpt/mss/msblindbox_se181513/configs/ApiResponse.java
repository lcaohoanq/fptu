
package com.fpt.mss.msblindbox_se181513.configs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
  private String status;
  private String message;
  private T data;
  private String errorCode;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh")
  private LocalDateTime timestamp = LocalDateTime.now();

  public ApiResponse(String status, String message, T data, String errorCode) {
    this.status = status;
    this.message = message;
    this.data = data;
    this.errorCode = errorCode;
  }
}
