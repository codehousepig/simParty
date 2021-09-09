package codehouse.simparty.service;

import codehouse.simparty.dto.*;
import codehouse.simparty.entity.Booking;
import codehouse.simparty.entity.Clothes;
import codehouse.simparty.entity.Image;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ClothesService {

    Long register(ClothesDTO clothesDTO); // C

    ClothesDTO read(Long cno); // R

    void modify(ClothesDTO clothesDTO); // U

    void remove(Long cno); // D

    PageResultDTO<ClothesDTO, Object[]> getList(PageRequestDTO requestDTO);

    default Map<String, Object> dtoToEntity(ClothesDTO clothesDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Clothes clothes = Clothes.builder()
                .cno(clothesDTO.getCno())
                .title(clothesDTO.getTitle())
                .keyword(clothesDTO.getKeyword())
                .build();
        entityMap.put("clothes", clothes);

        System.out.println("ClothesService_dtoToEntity_clothesDTO.getImageDTOList(): ");
        System.out.println(clothesDTO.getImageDTOList());
        List<ImageDTO> imageDTOList = clothesDTO.getImageDTOList();
        // ImageDTO 처리
        if (imageDTOList != null && imageDTOList.size() > 0) {
            List<Image> imageList = imageDTOList.stream().map(imageDTO -> {
                Image image = Image.builder()
                        .folderPath(imageDTO.getFolderPath())
                        .fileName(imageDTO.getFileName())
                        .uuid(imageDTO.getUuid())
                        .clothes(clothes)
                        .build();
                return image;
            }).collect(Collectors.toList());

            entityMap.put("imageList", imageList);
        } else {
            return null;
        }

        System.out.println("ClothesService_dtoToEntity_clothesDTO.getBookingDTOList(): ");
        System.out.println(clothesDTO.getBookingDTOList());
        List<BookingDTO> bookingDTOList = clothesDTO.getBookingDTOList();
        // bookingDTO 처리
        if (bookingDTOList != null && bookingDTOList.size() > 0) {
            List<Booking> bookingList = bookingDTOList.stream().map(bookingDTO -> {
                Booking booking = Booking.builder()
                        .startDate(bookingDTO.getStartDate())
                        .endDate(bookingDTO.getEndDate())
                        .clothes(clothes)
                        .build();

                return booking;
            }).collect(Collectors.toList());
            entityMap.put("bookingList", bookingList);
        } else {
            List<Booking> bookingList = null;
            entityMap.put("bookingList", bookingList);
        }

        return entityMap;
    }

    default ClothesDTO entityToDTO(Clothes clothes, List<Image> imageList, List<Booking> bookingList) {
        System.out.println("ClothesService_entityToDTO_Clothes: ");
        System.out.println(clothes);
        ClothesDTO clothesDTO = ClothesDTO.builder()
                .cno(clothes.getCno())
                .title(clothes.getTitle())
                .keyword(clothes.getKeyword())
                .regDate(clothes.getRegDate())
                .modDate(clothes.getModDate())
                .build();

        System.out.println("ClothesService_entityToDTO_List<Image>: ");
        System.out.println(imageList);
        List<ImageDTO> imageDTOList = imageList.stream().map(img -> {
            if (img == null) {
                return null;
            } else {
                return ImageDTO.builder()
                        .fileName(img.getFileName())
                        .folderPath(img.getFolderPath())
                        .uuid(img.getUuid())
                        .build();
            }
        }).collect(Collectors.toList());
        clothesDTO.setImageDTOList(imageDTOList);

        System.out.println("ClothesService_entityToDTO_List<Booking>: ");
        System.out.println(bookingList);
        List<BookingDTO> bookingDTOList = bookingList.stream().map(booking -> {
            if (booking == null) {
                System.out.println(BookingDTO.builder().build());
                return BookingDTO.builder().build();
            } else {
                return BookingDTO.builder()
                        .startDate(booking.getStartDate())
                        .endDate(booking.getEndDate())
                        .build();
            }
        }).collect(Collectors.toList());
        clothesDTO.setBookingDTOList(bookingDTOList);

        return clothesDTO;
    }
}
