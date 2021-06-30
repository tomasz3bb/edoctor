package pl.edu.wszib.edoctor.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePhotoModel {
    @Lob
    private Byte[] oldImage;
    @Lob
    private Byte[] newImage;
}
