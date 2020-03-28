package com.ccsu.feng.test;

import com.ccsu.feng.test.dao.DeedsDetailMapper;
import com.ccsu.feng.test.dao.UserBasesMapper;
import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.entity.DeedsDetail;
import com.ccsu.feng.test.entity.UserBases;
import com.ccsu.feng.test.repository.DeedsNodeRepository;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.service.node.IPersonNodeService;
import com.ccsu.feng.test.task.ReadNumScheduledTask;
import com.ccsu.feng.test.utils.NumberUtils;
import com.ccsu.feng.test.utils.RedisUtil;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForReadableInstant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.util.ListUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    IPersonNodeService iPersonNodeService;

    @Autowired
    ReadNumScheduledTask readNumScheduledTask;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserBasesMapper userBasesMapper;

    @Autowired
    DeedsNodeRepository deedsNodeRepository;
    @Autowired
    IBaseRelationshipService iBaseRelationshipService;
    @Autowired
    DeedsDetailMapper deedsDetailMapper;



    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

    @Test
    public void contextLoads() {
        DeedsDetail deedsDetail =new DeedsDetail();
        deedsDetail.setName("第三难满月抛江");
        deedsDetail.setContent("<p>道德经第三章，不尚贤，使民不争;不贵难得之货，使民不为盗;不见可欲，使民心不乱。按白话文解释是，不推崇有才能的人，免得使人们竞争;不珍视难得的货物，人们不去当盗贼;不看到引起贪欲的事物，使民心不扰乱。我们先看这三句。</p>\n" +
                "        <p>这里呢，我们可以去想一下，当年唐僧的父亲，他是高中状元，在街头正好赶上他的母亲抛绣球。当时唐僧的母亲一眼看中了陈光蕊，把绣球抛向了他。我相信看过电视剧的朋友都记得这一幕。那么在这里呢，就像前面说的几句话。是因为，他们有相应的一种心，所以呢，才有了后面的一些故事。</p>\n" +
                "        <p>什么心呢，就是“尚贤、贵难得之货、见可欲”。我们看道德经，以及看任何事物，我们都知道，任何事物是有两面性的。道德经，它也是正反两面说，阴阳两面说。这个就和我们地球有南极和北极两端是一样的。我们在分析事物的时候，我们能看到一面儿，我们就要去分析出来，另外一面儿。</p>\n" +
                "        <p>唐僧的母亲把唐僧生下来以后，南极星君，在她耳边类似像托梦状态，告诉她这孩子将被刘洪所害，你要尽快去处理，护他周全。她醒了以后，思来想去，决定呢，咬破手指写下血书，然后把孩子放在一块木板上，放到江流里，顺流而去。后面这孩子被长老救起以后叫江流儿，就是随着他当时这种状态。</p>\n" +
                "        <p>那么，我们思考下，当时他的母亲，为什么不放些珠宝呢，而是只是放了一份血书呢?</p>\n" +
                "        <p>为什么只放了血书没有放珠宝呢?其实就是第二句话“不贵难得之货，使民不为盗”。我们可以试想，当孩子遇上了好心人救他的时候，那么，是放了珠宝可以让对方有感觉，还是放血书会让对方有感觉。我们都知道，看到血书之后，每个人都会有怜悯之心，都会把他救下来，并且养大。</p>\n" +
                "        <p>他正好被寺院的长老所救，看到这封血书，也是为了唐僧后面一些故事引出一个很好开头儿。也就是说，他虽然是状元之子，但是，他的生命的走向，其实就已经被规划出来了。</p>\n" +
                "\n" +
                "        <p>后面一句就是，“是以圣人之治，虚其心，实其腹，弱其志，强其骨。”</p>\n" +
                "        <p>前面说了，我们能做的，就是顺着大势，尽可能美满幸福，这句话就告诉我们，怎样去美满幸福。其实就是不要想这么多，心里呢，多空一些，“虚其心”。按点儿去吃饭，实其腹。在之前的上一难，我们曾讲过，我们会有一些相应的志向，这些志向我们必须要去立，因为我们每个人都有相应的责任和使命。但是立完志向之后，我们要一步一个台阶去走，不要一下迈过去，一下也迈不过去。</p>\n" +
                "        <p>“弱其志，强其骨”。在这个之前，我们先把身体去弄好，强其骨，所以说呢，经常锻炼身体是必不可少的。</p>\n" +
                "        <p>当下社会，发展这么迅猛的一个态势，我们更要回归自己的本心，尤其是有压力的人，要去想一下为什么会有这种压力，其实有一个原因就是不自信的状态。可以试想，我们可以左右的事物太少了，身体、疾病很难左右，事业很难左右。所以说我们能去左右的，就是把当下做好，当下开心，当下做的让自己满意。</p>\n" +
                "        <p>就像唐僧的母亲能去做到的，就是把她孩子放到木盆里，写封血书，给孩子穿好衣服，让孩子随波逐流，能漂到哪儿漂到哪儿。</p>\n" +
                "        <p>在随波逐流当中，其实就好比是我们人生是一样的，我们在一个木盘里面，然后上面有我们相应的一些资料，然后我们就这样顺着大势去走了。其实走到哪儿，我们也不太清楚。我们即使想走的很好，但是我们也没有办法，会按照我们自己的意愿完完整整去做到。</p>\n" +
                "        <p>所以这时候我们可以去选择放下，然后接纳现在的自己。那同样的，反而会出现新的光明、新的生机。</p>\n" +
                "        <p>好，这一篇就先讲到这里，谢谢您的聆听，感谢关注静闻道，期待您的留言，一起交流探讨。明天再见。</p>");
        deedsDetail.setDetailId(456);
        deedsDetail.setPreDeedsUrl("/page/xi/deedsDetail/413");
        deedsDetail.setPreDeedsName("第一难金蝉遭贬");
        deedsDetail.setNextDeedsUrl("/page/xi/deedsDetail/420");
        deedsDetail.setNextDeedsName("第四难寻亲报冤");

        deedsDetailMapper.insert(deedsDetail);
    }


    @Test
    public void T(){
      readNumScheduledTask.syncPostViews();
    }



}
